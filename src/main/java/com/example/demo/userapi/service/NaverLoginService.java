package com.example.demo.userapi.service;

import com.example.demo.dto.response.LoginResponseDTO;
import com.example.demo.dto.response.NaverUserResponseDTO;
import com.example.demo.entity.User;
import com.example.demo.userapi.dto.response.KakaoUserDTO;
import com.example.demo.userapi.repository.UserRepository;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.apache.bcel.classfile.Code;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.Map;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class NaverLoginService {

    private final UserRepository userRepository;

    @Value("${naver.client_id}")
    private String NAVER_CLIENT_ID;
    @Value("${naver.client_secret}")
    private String NAVER_CLIENT_SECRET;
    @Value("${naver.client_callback_uri}")
    private String NAVER_CLIENT_CALLBACK_URI;
    @Value("${upload.path}")
    private String uploadRootPath;


    public void naverLogin(Map<String, String> params, HttpSession session) {
        String accessToken = getNaverAccessToken(params);
        NaverUserResponseDTO dto = getNaverUserInfo(accessToken);


        String phoneNumber = dto.getNaverAccount().getPhoneNumber();
        Optional<User> userOptional = userRepository.findByPhoneNumber(phoneNumber);

        User user;
        if (userOptional.isPresent()) {
            user = userOptional.get();
            user.changeAccessToken(accessToken);
        } else {
            user = User.builder()
                    .email(dto.getNaverAccount().getEmail())
                    .userName(dto.getNaverAccount().getProfile().getNickname())
                    .phoneNumber(dto.getNaverAccount().getPhoneNumber())
                    .profileImg(dto.getNaverAccount().getProfile().getProfileImageUrl())
                    .accessToken(accessToken)
                    .loginMethod(User.LoginMethod.NAVER)
                    .build();
            userRepository.save(user);
        }

        session.setAttribute("user", user);
    }

    private NaverUserResponseDTO getNaverUserInfo(String accessToken) {
        String userInfoUri = "https://openapi.naver.com/v1/nid/me";
        RestTemplate template = new RestTemplate();

        org.springframework.http.HttpHeaders headers = new org.springframework.http.HttpHeaders();
        headers.add("Authorization", "Bearer " + accessToken);

        ResponseEntity<NaverUserResponseDTO> responseEntity = template.exchange(
                userInfoUri,
                HttpMethod.GET,
                new HttpEntity<>(headers),
                NaverUserResponseDTO.class
        );

        return responseEntity.getBody();
    }



    public LoginResponseDTO naverService(String code) {
        // 인가 코드를 통해 토큰 발급받기
        String accessToken = getNaverAccessToken(code);
        log.info("token: {}", accessToken);

        NaverUser
    }

    private String getNaverAccessToken(String code) {

        // 요청 uri
        String requestUri = "https://nid.naver.com/oauth2.0/token";

        // 요청 헤더 설정
        HttpHeaders headers = new HttpHeaders();

        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("client_id", NAVER_CLIENT_ID);
        params.add("client_secret", NAVER_CLIENT_SECRET);
        params.add("grant_type", "authorization_code");
        params.add("code", code);
        params.add("redirect_uri", NAVER_CLIENT_CALLBACK_URI);

        RestTemplate template = new RestTemplate();
        HttpEntity<Object> requestEntity = new HttpEntity<>(params, headers);

        ResponseEntity<Map> responseEntity = template.exchange(requestUri, HttpMethod.POST, requestEntity, Map.class);
        Map<String, Object> responseJSON = (Map<String, Object>) responseEntity.getBody();
        return (String) responseJSON.get("access_token");
    }
}

