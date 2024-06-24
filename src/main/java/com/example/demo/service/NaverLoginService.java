package com.example.demo.service;

import com.example.demo.dto.response.LoginResponseDTO;
import com.example.demo.dto.response.NaverUserResponseDTO;
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
public class NaverLoginService {

    private final UserService userService;
    private final UserRepository userRepository;

    @Value("${naver.client_id}")
    private String NAVER_CLIENT_ID;
    @Value("${naver.client_secret}")
    private String NAVER_CLIENT_SECRET;
    @Value("${naver.client_callback_uri}")
    private String NAVER_CLIENT_CALLBACK_URI;
    @Value("${upload.path}")
    private String uploadRootPath;

    public LoginResponseDTO naverService(String code,String phoneNumber) {

        String accessToken = getNaverAccessToken(code);
        log.info("token: {}", accessToken);

        NaverUserResponseDTO userDTO = getNaverUserInfo(accessToken);
        log.info("userDTO: {}", userDTO);


        if (!userService.isDuplicatePhone(userDTO.getNaverUserDetail().getPhoneNumber())
                || !userService.isDuplicateEmail(userDTO.getNaverUserDetail().getEmail())) {
            User saved = userRepository.save(userDTO.toEntity(accessToken,phoneNumber));
        }

        Optional<User> foundUser
                = userRepository.findByEmail(userDTO.getNaverUserDetail().getPhoneNumber());

        Map<String, String> token = userService.getTokenMap(foundUser.orElse(null));
        foundUser.get().changeAccessToken(accessToken);

        return new LoginResponseDTO(foundUser.orElse(null), token);
    }

    private NaverUserResponseDTO getNaverUserInfo(String accessToken) {
        String requestUri = "https://openapi.naver.com/v1/nid/me";

        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + accessToken);

        RestTemplate template = new RestTemplate();
        ResponseEntity<NaverUserResponseDTO> responseEntity
                = template.exchange(requestUri, HttpMethod.POST, new HttpEntity<>(headers), NaverUserResponseDTO.class);

        log.info("userInfo: {}", responseEntity.getBody());
        return responseEntity.getBody();
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

