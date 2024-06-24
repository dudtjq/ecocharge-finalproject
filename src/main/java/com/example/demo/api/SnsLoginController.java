package com.example.demo.api;

import com.example.demo.dto.response.LoginResponseDTO;
import com.example.demo.service.GoogleLoginService;
import com.example.demo.service.KaKaoLoginService;
import com.example.demo.service.NaverLoginService;
import com.example.demo.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class SnsLoginController {

    private final KaKaoLoginService kaKaoLoginService;
    private final NaverLoginService naverLoginService;
    private final GoogleLoginService googleLoginService;
    private final UserService userService;

    @GetMapping("/kakaologin")
    public ResponseEntity<?> kakaoLogin(String code,String phoneNumber) {
        log.info("/api/auth/kakaoLogin - GET! code: {}", code);
        log.info("phoneNumber: {}", phoneNumber);
        LoginResponseDTO dto = kaKaoLoginService.kakaoService(code,phoneNumber);
        LoginResponseDTO responseDTO = userService.authenticate(dto);
        log.info("responseDTO: {}", responseDTO);

        return ResponseEntity.ok().body(responseDTO);
    }


    @GetMapping("/naverlogin")
    public ResponseEntity<?> naverLogin(String code,String phoneNumber) {
        log.info("/api/auth/naverLogin - GET! code: {}", code);
        LoginResponseDTO dto = naverLoginService.naverService(code,phoneNumber);
        LoginResponseDTO responseDTO = userService.authenticate(dto);

        return ResponseEntity.ok().body(responseDTO);
    }

    @GetMapping("/googlelogin")
    public ResponseEntity<?> googleLogin(String code,String phoneNumber) {
        log.info("/api/auth/googleLogin - GET! code: {}", code);
        LoginResponseDTO dto = googleLoginService.googleService(code,phoneNumber);
        LoginResponseDTO responseDTO = userService.authenticate(dto);

        return ResponseEntity.ok().body(responseDTO);
    }

}





