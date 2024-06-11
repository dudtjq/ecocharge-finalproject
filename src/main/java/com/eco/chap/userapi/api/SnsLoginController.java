package com.eco.chap.userapi.api;

import com.eco.chap.userapi.service.GoogleLoginService;
import com.eco.chap.userapi.service.KaKaoLoginService;
import com.eco.chap.userapi.service.NaverLoginService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.HashMap;
import java.util.Map;

@Controller
@Slf4j
@RequiredArgsConstructor
public class SnsLoginController {

    private final KaKaoLoginService kaKaoLoginService;
    private final NaverLoginService naverLoginService;
    private final GoogleLoginService googleLoginService;

    @Value("${sns.kakao.app-key}")
    private String kakaoAppKey;

    @Value("${sns.kakao.redirect-uri}")
    private String kakaoRidirectUri;

    @Value("${sns.naver.client-id}")
    private String naverClientId;

    @Value("${sns.naver.client-secret}")
    private String naverClientSecret;

    @Value("${sns.naver.client-callback-uri}")
    private String naverCallbakUri;

    @Value("${sns.naver.state}")
    private String naverState;

    @Value("${sns.google.login.url}")
    private String googleLoginUrl;

    @Value("${sns.google.redirect.uri}")
    private String googleRedirectUri;

    @Value("${sns.google.client-id}")
    private String googleClientId;

    @Value("${sns.google.client-pw}")
    private String googleClientPw;

    @GetMapping("/kakao/login")
    public String kakaoLogin() {
        String uri = "https://kauth.kakao.com/oauth/authorize";
        uri += "?client_id=" + kakaoAppKey;
        uri += "&redirect_uri=" + kakaoRidirectUri;
        uri += "&response_type=code";

        return "redirect:" + uri;
    }

    // 카카오 인가 코드 받기
    @GetMapping("/auth/kakao")
    public String snsKakao(String code, HttpSession session) {
        log.info("카카오 로그인 인가 코드:{}", code);

        // 인가 코드를 가지고 카카오 인증 서버에 토큰 발급 요청을 보내자
        // 서비스에게 시킬 것 (server to server 통신)
        Map<String, String> params = new HashMap<>();
        params.put("appKey", kakaoAppKey);
        params.put("redirect", kakaoRidirectUri);
        params.put("code", code);

        kaKaoLoginService.kakaoLogin(params, session);

        // 로그인 처리가 모두 완료되면 홈 화면으로
        return "redirect:/home/main";
    }

    @GetMapping("/naver/login")
    public String naverConnect() {
        // state용 난수 생성
        SecureRandom random = new SecureRandom();
        String state = new BigInteger(130, random).toString(32);

        String uri = "https://nid.naver.com/oauth2.0/authorize?";
        uri += "response_type=code";
        uri += "&client_id=" + naverClientId;
        uri += "&redirect_uri=" + naverCallbakUri;
        uri += "&state=" + state;

        return "redirect:" + uri;

    }

    // 네이버 인가코드 받기
    @GetMapping("/auth/naver")
    private String snsNaver(String code, HttpSession session) {
        log.info("네이버 로그인 인가 코드:{}", code);

        // 인가 코드를 가지고 네이버 인증 서버에 토큰 발급 요청을 보내자
        Map<String, String> params = new HashMap<>();
        params.put("client_id", naverClientId);
        params.put("client_secret", naverClientSecret);
        params.put("grant_type", "authorization_code");
        params.put("state", naverState);
        params.put("code", code);

        naverLoginService.naverLogin(params, session);

        return "redirect:/home/main";
    }

    @GetMapping("/google/login")
    public String googleLogin() {
        String uri = googleLoginUrl + "/o/oauth2/v2/auth";
        uri += "?client_id=" + googleClientId;
        uri += "&redirect_uri=" + googleRedirectUri;
        uri += "&response_type=code&scope=email%20profile%20openid&access_type=offline";

        return "redirect:" + uri;
    }

    //     구글 인가코드 받기
    @GetMapping("/auth/google")
    private String snsGoogle(String code, HttpSession session) {
        log.info("구글 로그인 인가 코드:{}", code);

        // 인가 코드를 가지고 구글 인증 서버에 토큰 발급 요청을 보내자
        Map<String, String> params = new HashMap<>();
        params.put("client_id", googleClientId);
        params.put("client_secret", googleClientPw);
        params.put("code", code);
        params.put("redirect_uri", googleRedirectUri);
        params.put("grant_type", "authorization_code");

        googleLoginService.googleLogin(params, session);

        return "redirect:/home/main";
    }


}


