package com.example.demo.api;

import com.example.demo.auth.TokenUserInfo;
import com.example.demo.dto.request.LoginRequestDTO;
import com.example.demo.dto.request.UserSignUpRequestDTO;
import com.example.demo.dto.request.ModifyUserRequestDTO;
import com.example.demo.dto.response.LoginResponseDTO;
import com.example.demo.dto.response.ModifyUserResponseDTO;
import com.example.demo.dto.response.UserResponseDTO;
import com.example.demo.dto.response.UserSignUpResponseDTO;
import com.example.demo.entity.User;
import com.example.demo.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;


import java.util.Map;


@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class UserController {

    private final UserService userService;

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
    public ResponseEntity<?> signIn (@RequestBody LoginRequestDTO dto,
                                     BindingResult result
    ) {
        log.info("/api/auth/signin - POST - {}", dto);

        log.info("result: {}", result);
        ResponseEntity<FieldError> response = getFieldErrorResponseEntity(result);
        log.info("response : {}", response);
        if (response != null) return response;

        LoginResponseDTO responseDTO = userService.authenticate(dto);
        log.info("responseDTO: {}", responseDTO);
        return ResponseEntity.ok().body(responseDTO);

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
        if (renewalAccessToken != null) {
            return ResponseEntity.ok().body(Map.of("access_token", renewalAccessToken));
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
    private ResponseEntity<ResponseEntity<User>> pwChange(@RequestBody String password, String phoneNumber) {
        ResponseEntity<User> findPassword= userService.changePassword( password,phoneNumber);
        return ResponseEntity.ok().body(findPassword);
    }

    @PostMapping("/showid")
    private ResponseEntity<?>showId(@RequestBody String phoneNumber) {
        ResponseEntity<User> showid=userService.showid(phoneNumber);
        return ResponseEntity.ok().body(showid);
    }

    // 회원 정보 렌더링
    @PostMapping("/myPage")
    public ResponseEntity<?> myInfo (@RequestBody String phoneNumber) {
        log.info("/myPage POST 요청 - phoneNumber: {}", phoneNumber);
        UserResponseDTO foundUser = userService.findUser(phoneNumber);
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