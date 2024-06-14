package com.example.demo.userapi.service;

import com.example.demo.auth.TokenProvider;
import com.example.demo.dto.response.GoogleUserResponseDTO;
import com.example.demo.entity.User;
import com.example.demo.userapi.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import jakarta.servlet.http.HttpSession;

import java.util.Map;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class GoogleLoginService {

    private final UserRepository userRepository;
    private final TokenProvider tokenProvider;

    public void googleLogin(Map<String, String> params, HttpSession session) {
        String accessToken = getGoogleAccessToken(params);
        GoogleUserResponseDTO dto = getGoogleUserInfo(accessToken);

        String email = dto.getPhoneNumber();
        Optional<User> userOptional = userRepository.findByPhoneNumber(dto.getPhoneNumber());

        User user;
        if (userOptional.isPresent()) {
            user = userOptional.get();
            user.changeAccessToken(accessToken);
        } else {
            user = User.builder()
                    .email(email)
                    .phoneNumber(dto.getPhoneNumber())
                    .profileImg(dto.getProfileImage())
                    .accessToken(accessToken)
                    .loginMethod(User.LoginMethod.GOOGLE)
                    .build();
            userRepository.save(user);
        }

        session.setAttribute("user", user);
    }

    private GoogleUserResponseDTO getGoogleUserInfo(String accessToken) {
        String userInfoUri = "https://www.googleapis.com/oauth2/v3/userinfo";
        RestTemplate template = new RestTemplate();

        org.springframework.http.HttpHeaders headers = new org.springframework.http.HttpHeaders();
        headers.add("Authorization", "Bearer " + accessToken);

        ResponseEntity<GoogleUserResponseDTO> responseEntity = template.exchange(
                userInfoUri,
                HttpMethod.GET,
                new HttpEntity<>(headers),
                GoogleUserResponseDTO.class
        );

        return responseEntity.getBody();
    }

    private String getGoogleAccessToken(Map<String, String> requestParams) {
        String requestUri = "https://oauth2.googleapis.com/token";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("code", requestParams.get("code"));
        params.add("client_id", requestParams.get("client_id"));
        params.add("client_secret", requestParams.get("client_secret"));
        params.add("redirect_uri", requestParams.get("redirect_uri"));
        params.add("grant_type", "authorization_code");

        RestTemplate template = new RestTemplate();
        HttpEntity<Object> requestEntity = new HttpEntity<>(params, headers);

        ResponseEntity<Map> responseEntity = template.exchange(requestUri, HttpMethod.POST, requestEntity, Map.class);
        Map<String, Object> responseJSON = (Map<String, Object>) responseEntity.getBody();
        return (String) responseJSON.get("access_token");
    }
}

