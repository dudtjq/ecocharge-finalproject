package com.example.demo.controller;

import com.example.demo.auth.TokenProvider;
import com.example.demo.auth.TokenUserInfo;
import com.example.demo.dto.request.LoginRequestDTO;
import com.example.demo.dto.request.UserSignUpRequestDTO;
import com.example.demo.dto.request.ModifyUserRequestDTO;
import com.example.demo.dto.response.LoginResponseDTO;
import com.example.demo.dto.response.ModifyUserResponseDTO;
import com.example.demo.dto.response.UserResponseDTO;
import com.example.demo.dto.response.UserSignUpResponseDTO;
import com.example.demo.entity.User;
import com.example.demo.entity.User;
import com.example.demo.filter.JwtAuthFilter;
import com.example.demo.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import software.amazon.awssdk.services.s3.endpoints.internal.Value;


import java.util.Map;


@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class UserController {

    private final UserService userService;
    private final TokenProvider tokenProvider;
    private final JwtAuthFilter jwtAuthFilter;

    // 회원 가입 요청 처리
    // POST: /api/auth
    @PostMapping
    public ResponseEntity<?> signUp( @RequestPart("user") UserSignUpRequestDTO dto) {
        log.info("/api/auth POST! - {}", dto);

        try {
//            String uploadedFilePath = null;
            // profileImage 처리 로직은 필요에 따라 추가적으로 구현 가능

            UserSignUpResponseDTO responseDTO = userService.create(dto);
            return ResponseEntity.ok().body(responseDTO);
        } catch (Exception e) {
            log.error("Error processing signUp request", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PostMapping("/signin")
    public ResponseEntity<?> signIn(@RequestBody LoginRequestDTO dto, BindingResult result) {
        log.info("/api/auth/signin - POST - {}", dto);

        // 유효성 검사 결과를 확인하고 에러가 있으면 에러 응답을 반환합니다.
        ResponseEntity<FieldError> response = getFieldErrorResponseEntity(result);
        log.info("response : {}", response);
        if (response != null) {
            return response;
        }

        try {
            // 사용자 서비스를 통해 인증을 시도합니다.
            LoginResponseDTO responseDTO = userService.authenticate(dto);
            log.info("responseDTO: {}", responseDTO);
            return ResponseEntity.ok().body(responseDTO);
        } catch (AuthenticationException e) {
            // 인증 예외가 발생한 경우, 예외 메시지를 클라이언트에게 전달합니다.
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");
        } catch (Exception e) {
            // 그 외의 예외가 발생한 경우, 400 Bad Request 상태 코드와 예외 메시지를 클라이언트에게 반환합니다.
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/check")
    public ResponseEntity<?> check (@RequestBody String identify) {
        log.info("아이디:{}",identify);
        if (identify.trim().isEmpty()) {
            return ResponseEntity.badRequest()
                    .body("ID가 없습니다.");
        }
        boolean resultFlag = userService.isDuplicateIdentify(identify);
        log.info("중복확인 :{}", resultFlag);
        return ResponseEntity.ok().body(resultFlag);
    }

    // 로그아웃 처리
    @GetMapping("/logout")
    public ResponseEntity<?> logout(
            @AuthenticationPrincipal TokenUserInfo userInfo
    ) {
        log.info("/api/auth/logout - GET! - user: {}", userInfo.getEmail());

        String result = userService.logout(userInfo);

        return ResponseEntity.ok().body(result);
    }

    // 리프레쉬 토큰을 활용한 엑세스 토큰 재발급 요청
    @PostMapping("/refresh")
    public ResponseEntity<?> refresh(@RequestBody Map<String, String> tokenRequest) {
        log.info("/api/auth/refresh: POST! - tokenRequest: {}", tokenRequest);
        String renewalAccessToken = userService.renewalAccessToken(tokenRequest);
        log.info("renewalAccessToken: {}", renewalAccessToken);
        if (renewalAccessToken != null) {
            log.info("map: {}",Map.of("accessToken", renewalAccessToken));
            return ResponseEntity.ok().body(Map.of("accessToken", renewalAccessToken));
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid refresh token");
    }


    private static ResponseEntity<FieldError> getFieldErrorResponseEntity(BindingResult result) {
        if (result.hasErrors()) {
            log.warn(result.toString());
            return ResponseEntity.badRequest()
                    .body(result.getFieldError());
        }
        return null;
    }

    @PostMapping("/pwsearch")
    private ResponseEntity<?> pwChange(@RequestBody ModifyUserRequestDTO Request) {
        log.info("request:{}",Request);
         userService.changePassword(Request);
        log.info("서비스 로직을 거침");
        return ResponseEntity.ok().body("변경 완료");
    }

    @PostMapping("/showid")
    private ResponseEntity<?>showId(@RequestBody String phoneNumber) {
        String showid=userService.showid(phoneNumber);
        log.info(showid);
        return ResponseEntity.ok().body(showid);
    }

    @GetMapping("/validate")
    public ResponseEntity<?> validatedToken () {
        return ResponseEntity.ok().body("권한이 있는 사용자입니다.");
    }

    // 회원 정보 렌더링
    @PostMapping("/myPage")
    public ResponseEntity<?> myInfo (@RequestBody String id) {
        log.info("/myPage POST 요청 - id: {}", id);
        UserResponseDTO foundUser = userService.findUser(id);
        log.info("/myPage foundUser: {}", foundUser);
        return ResponseEntity.ok().body(foundUser);
    }

    // 회원 정보 수정
    @PostMapping("/modify")
    public ResponseEntity<?> modifyMyInfo(@RequestBody ModifyUserRequestDTO requestDTO) {
        log.info("/modify POST 요청 - requestDTO: {}", requestDTO);
        ModifyUserResponseDTO responseDTO = userService.modifyUserInfo(requestDTO);
        log.info("/modifyResponseDTO: {}", responseDTO);
        return ResponseEntity.ok().body(responseDTO);

    }


}