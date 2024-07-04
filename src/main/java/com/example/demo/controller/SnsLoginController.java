package com.example.demo.controller;

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
        ResponseEntity<?> responseDTO = kaKaoLoginService.kakaoService(code,phoneNumber);

        return ResponseEntity.ok().body(responseDTO);
    }


    @GetMapping("/naverlogin")
    public ResponseEntity<?> naverLogin(String code,String phoneNumber) {
        log.info("/api/auth/naverLogin - GET! code: {}", code);
        LoginResponseDTO responseDTO = naverLoginService.naverService(code,phoneNumber);

        return ResponseEntity.ok().body(responseDTO);
    }

    @GetMapping("/googlelogin")
    public ResponseEntity<?> googleLogin(String code,String phoneNumber) {
        log.info("/api/auth/googleLogin - GET! code: {}", code);
        log.info("phoneNumber: {}", phoneNumber);
        LoginResponseDTO responseDTO = googleLoginService.googleService(code,phoneNumber);
        return ResponseEntity.ok().body(responseDTO);
    }

}