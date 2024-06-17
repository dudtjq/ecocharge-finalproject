package com.example.demo.userapi.service;

import com.example.demo.dto.response.LoginResponseDTO;
import com.example.demo.dto.response.NaverUserResponseDTO;
import com.example.demo.entity.User;
import com.example.demo.userapi.repository.UserRepository;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
    private final UserService userService;

    public LoginResponseDTO naverLogin(Map<String, String> params, HttpSession session) {
        String accessToken = getNaverAccessToken(params);
        NaverUserResponseDTO dto = getNaverUserInfo(accessToken);


        String phoneNumber = dto.getNaverAccount().getPhoneNumber();
        Optional<User> userOptional = userRepository.findByphoneNumber(phoneNumber);

        User user;
        if (userOptional.isPresent()) {
            user = userOptional.get();
            user.changeNaverAccessToken(accessToken);
        } else {
            user = User.builder()
                    .email(dto.getNaverAccount().getEmail())
                    .userName(dto.getNaverAccount().getProfile().getNickname())
                    .phoneNumber(dto.getNaverAccount().getPhoneNumber())
                    .profileImg(dto.getNaverAccount().getProfile().getProfileImageUrl())
                    .naverAccessToken(accessToken)
                    .loginMethod(User.LoginMethod.NAVER)
                    .build();
            userRepository.save(user);
        }
        Map<String, String> token = userService.getTokenMap(user);
        session.setAttribute("user", user);
        return new LoginResponseDTO(user,token);
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

    private String getNaverAccessToken(Map<String, String> requestParams) {
        String requestUri = "https://nid.naver.com/oauth2.0/token";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("client_id", requestParams.get("client_id"));
        params.add("client_secret", requestParams.get("client_secret"));
        params.add("grant_type", "authorization_code");
        params.add("code", requestParams.get("code"));
        params.add("redirect_uri", requestParams.get("redirect_uri"));

        RestTemplate template = new RestTemplate();
        HttpEntity<Object> requestEntity = new HttpEntity<>(params, headers);

        ResponseEntity<Map> responseEntity = template.exchange(requestUri, HttpMethod.POST, requestEntity, Map.class);
        Map<String, Object> responseJSON = (Map<String, Object>) responseEntity.getBody();
        return (String) responseJSON.get("access_token");
    }
}

