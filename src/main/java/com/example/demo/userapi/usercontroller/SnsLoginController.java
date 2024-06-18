package com.example.demo.userapi.usercontroller;

import com.example.demo.userapi.dto.response.LoginResponseDTO;
import com.example.demo.userapi.service.GoogleLoginService;
import com.example.demo.userapi.service.KaKaoLoginService;
import com.example.demo.userapi.service.NaverLoginService;
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

    @GetMapping("/kakaologin")
    public ResponseEntity<?> kakaoLogin(String code) {
        log.info("/api/auth/kakaoLogin - GET! code: {}", code);
        LoginResponseDTO responseDTO = kaKaoLoginService.kakaoService(code);

        return ResponseEntity.ok().body(responseDTO);
    }


    @GetMapping("/naverlogin")
    public ResponseEntity<?> naverLogin(String code) {
        log.info("/api/auth/naverLogin - GET! code: {}", code);
        LoginResponseDTO responseDTO = naverLoginService.naverService(code);

        return ResponseEntity.ok().body(responseDTO);
    }

    @GetMapping("/googlelogin")
    public ResponseEntity<?> googleLogin(String code) {
        log.info("/api/auth/googleLogin - GET! code: {}", code);
        googleLoginService.googleService(code);
        return null;
    }

}





