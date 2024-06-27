package com.example.demo.service;

import com.example.demo.auth.TokenProvider;
import com.example.demo.dto.response.GoogleUserResponseDTO;
import com.example.demo.dto.response.LoginResponseDTO;
import com.example.demo.entity.User;
import com.example.demo.repository.UserRepository;
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
public class GoogleLoginService {

    private final UserRepository userRepository;
    private final TokenProvider tokenProvider;
    private final UserService userService;

    @Value("${google.client_id}")
    private String GOOGLE_CLIENT_ID;

    @Value("${google.client_pw}")
    private String GOOGLE_CLIENT_PW;

    @Value("${google.redirect_url}")
    private String GOOGLE_REDIRECT_URL;

    public LoginResponseDTO googleService(String code,String phoneNumber) {
        String accessToken = getGoogleAccessToken(code);
        log.info("accessToken: {}", accessToken);

        GoogleUserResponseDTO userDTO = getGoogleUserInfo(accessToken);
        log.info("userDTO: {}", userDTO);

        if (!userService.isDuplicatePhone(phoneNumber)){
           userRepository.save(userDTO.toEntity(accessToken, phoneNumber));
        }

        // 이메일이 중복됐다? -> 이전에 로그인 한 적이 있다. -> DB에 데이터를 또 넣을 필요는 없다.
        User foundUser
                = userRepository.findByPhoneNumber(phoneNumber);

        Map<String, String> token = userService.getTokenMap(foundUser);
        log.info("token: {}", token);
        return new LoginResponseDTO(foundUser, token);
    }

    private GoogleUserResponseDTO getGoogleUserInfo(String accessToken) {
        String userInfoUri = "https://www.googleapis.com/oauth2/v3/userinfo";

        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + accessToken);

        RestTemplate template = new RestTemplate();
        ResponseEntity<GoogleUserResponseDTO> responseEntity
                = template.exchange(userInfoUri, HttpMethod.GET, new HttpEntity<>(headers), GoogleUserResponseDTO.class
        );

        return responseEntity.getBody();
    }

    private String getGoogleAccessToken(String code) {
        String requestURI = "https://oauth2.googleapis.com/token";


        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("code", code);
        params.add("client_id", GOOGLE_CLIENT_ID);
        params.add("client_secret", GOOGLE_CLIENT_PW);
        params.add("redirect_uri", GOOGLE_REDIRECT_URL);
        params.add("grant_type", "authorization_code");

        HttpEntity<Object> requestEntity = new HttpEntity<>(params, headers);

        RestTemplate template = new RestTemplate();

        ResponseEntity<Map> responseEntity
                = template.exchange(requestURI, HttpMethod.POST, requestEntity, Map.class);
        Map<String, Object> responseData = (Map<String, Object>) responseEntity.getBody();
        return (String) responseData.get("access_token");
    }
}