package com.example.demo.userapi.service;

import com.example.demo.dto.response.KakaoUserResponseDTO;
import com.example.demo.entity.User;
import com.example.demo.userapi.repository.UserRepository;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
public class KaKaoLoginService {

    private final UserRepository userRepository;

    public void kakaoLogin(Map<String, String> params, HttpSession session) {
        String accessToken = getKakaoAccessToken(params);
        KakaoUserResponseDTO dto = getKakaoUserInfo(accessToken);

        String phoneNumber = dto.getPhoneNumber();
        Optional<User> userOptional = userRepository.findByPhoneNumber(phoneNumber);

        User user;
        if (userOptional.isPresent()) {
            user = userOptional.get();
            user.changeKakaoAccessToken(accessToken);
        } else {
            user = User.builder()
                    .email(dto.getKakaoAccount().getEmail())
                    .userName(dto.getKakaoAccount().getProfile().getNickname())
                    .profileImg(dto.getKakaoAccount().getProfile().getProfileImageUrl())
                    .kakaoAccessToken(accessToken)
                    .loginMethod(User.LoginMethod.KAKAO)
                    .build();
            userRepository.save(user);
        }

        session.setAttribute("user", user);
    }

    private KakaoUserResponseDTO getKakaoUserInfo(String accessToken) {
        String userInfoUri = "https://kapi.kakao.com/v2/user/me";
        RestTemplate template = new RestTemplate();

        org.springframework.http.HttpHeaders headers = new org.springframework.http.HttpHeaders();
        headers.add("Authorization", "Bearer " + accessToken);

        ResponseEntity<KakaoUserResponseDTO> responseEntity = template.exchange(
                userInfoUri,
                HttpMethod.GET,
                new HttpEntity<>(headers),
                KakaoUserResponseDTO.class
        );

        return responseEntity.getBody();
    }

    private String getKakaoAccessToken(Map<String, String> requestParams) {
        String requestUri = "https://kauth.kakao.com/oauth/token";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("grant_type", "authorization_code");
        params.add("client_id", requestParams.get("client_id"));
        params.add("redirect_uri", requestParams.get("redirect_uri"));
        params.add("code", requestParams.get("code"));

        RestTemplate template = new RestTemplate();
        HttpEntity<Object> requestEntity = new HttpEntity<>(params, headers);

        ResponseEntity<Map> responseEntity = template.exchange(requestUri, HttpMethod.POST, requestEntity, Map.class);
        Map<String, Object> responseJSON = (Map<String, Object>) responseEntity.getBody();
        return (String) responseJSON.get("access_token");
    }
}

