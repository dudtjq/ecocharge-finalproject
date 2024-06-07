package com.example.demo.controller;
import com.example.demo.service.SnsLoginService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.HashMap;
import java.util.Map;

@Controller
@RequiredArgsConstructor
@Slf4j
public class SnsLoginController {

    private final SnsLoginService snsLoginService;

    //google
    @Value("${spring.security.oauth2.client.registration.google.client-id}")
    private String GoogleClientId;
    @Value("${spring.security.oauth2.client.registration.google.redirect-uri}")
    private String GoogleRedirectUri;

    //naver
    @Value("${sns.naver.client-id}")
    private String naverClientId;
    @Value("${sns.naver.client-secret}")
    private String clientSecret;
    @Value("${sns.naver.redirect-uri}")
    private String naverRedirectUri;
    @Value("${sns.naver.state}")
    private String state;
    @Value("${sns.naver.grant_type")
    private String grantType;

    //kakao
    @Value("${sns.kakao.app-key}")
    private String kakaoAppKey;

    @Value("${sns.kakao.redirect-uri}")
    private String kakaoRedirectUri;


    //구글 로그인
    @GetMapping("/google/login")
    public String googleLogin() {
        String authorizationUri = "https://accounts.google.com/o/oauth2/auth";
        String scope = "openid email profile";
        String responseType = "code";
        String uri = authorizationUri + "?client_id=" + GoogleClientId + "&redirect_uri=" + GoogleRedirectUri+
                "&response_type=" + responseType + "&scope=" + scope;
        return "redirect:" + uri;
    }

    @GetMapping("/auth/google")
    public String snsGoogle( String code, HttpSession session) {
        Map<String, String> params = new HashMap<>();
        params.put("appKey",  GoogleClientId);
        params.put("redirect",GoogleRedirectUri);
        params.put("code", code);

        snsLoginService.googleLogin(params, session);
        return "redirect:/index";
    }


    //네이버로그인
    @GetMapping("/naver/login")
    private String naverLogin(){
        String  uri = "https://nid.naver.com/oauth2.0/authorize";
        uri += "?response_type=code";
        uri += "&client_id="+naverClientId;
        uri += "&state="+state;
        uri += "&redirect_uri="+naverRedirectUri;
        log.info("url: {}", uri);
        return "redirect:"+uri;

    }

    @GetMapping("/auth/naver")
    public String naverCallback(HttpSession session ,String code, String state) {
        log.info("/auth/naver: GET!");

        log.info("code: {}, state: {}", code, state);

        Map<String , String > naverParams = new HashMap<>();
        naverParams.put("grant_type", "authorization_code");
        naverParams.put("client_id", naverClientId);
        naverParams.put("client_secret", clientSecret);
        naverParams.put("code", code);
        naverParams.put("state", state);


        SnsLoginService.naverLogin(naverParams , session);
        return "redirect:/index";
    }

    //카카오 로그인
    @GetMapping("/kakao/login")
    public String kakaoLogin() {
        String uri = "https://kauth.kakao.com/oauth/authorize";
        uri += "?client_id=" + kakaoAppKey;
        uri += "&redirect_uri=" + kakaoRedirectUri;
        uri += "&response_type=code";
        uri += "&prompt=login";
        return "redirect:" + uri;
    }

    @GetMapping("/auth/kakao")
    public String snsKakao(String code , HttpSession session) {
        log.info("카카오 로그인 인가코드: {}", code);
        Map<String, String> params = new HashMap<>();
        params.put("appKey", kakaoAppKey);
        params.put("redirect", kakaoRedirectUri);
        params.put("code", code);

        snsLoginService.kakaoLogin(params, session);



        return "redirect:/index";
    }




}