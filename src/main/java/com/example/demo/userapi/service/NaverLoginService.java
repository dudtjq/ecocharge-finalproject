package com.example.demo.userapi.service;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Service
@Slf4j
@RequiredArgsConstructor
public class NaverLoginService {

    private final UserService userService;

    // 네이버 로그인
    public void naverLogin(Map<String, String> params, HttpSession session) {

        String accessToken = getNaverAccessToken(params);
        log.info("access_token: {}", accessToken);

        // 전달받은 엑세스 토큰을 활용하여 사용자의 정보 가져오기
        NaverUserResponseDTO dto = getNaverUserInfo(accessToken);

        // 네이버에서 받은 회원정보로 우리 사이트 회원가입
        String id = dto.getNaverUserDetail().getId();
        log.info("네이버 소셜 아이디: {}", id);

        // 회원 중복 확인 (아이디)
        if (!userService.checkDuplicateValue("account", id)) {
            // 한 번도 네이버 로그인을 한 적이 없다면 회원가입 진행
            userService.join(
                    SignUpRequestDto.builder()
                            .accountNumber(dto.getNaverUserDetail().getId())
                            .password("0000")
                            .name(dto.getNaverUserDetail().getName())
                            .email(dto.getNaverUserDetail().getEmail())
                            .birthday(Integer.parseInt(dto.getNaverUserDetail().getBirthyear()))
                            .gender(dto.getNaverUserDetail().getGender())
                            .nickname(dto.getNaverUserDetail().getNickname())
                            .loginMethod(User.LoginMethod.NAVER)
                            .build(),
                    dto.getNaverUserDetail().getProfileImage()
            );
        }

//         우리 사이트 로그인 처리
        userService.maintainLoginState(session, id);

    }

    private NaverUserResponseDTO getNaverUserInfo(String accessToken) {

        String requestUri = "https://openapi.naver.com/v1/nid/me";

        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + accessToken);

        // 요청 보내기
        RestTemplate template = new RestTemplate();
        ResponseEntity<NaverUserResponseDTO> responseEntity = template.exchange(
                requestUri,
                HttpMethod.POST,
                new HttpEntity<>(headers),
                NaverUserResponseDTO.class
        );

        NaverUserResponseDTO responseJSON = responseEntity.getBody();
        log.info("응답 데이터 결과 : {}", responseJSON);

        return responseJSON;

    }

    private String getNaverAccessToken(Map<String, String> requestParams) {

        // 요청 URI
        String requestUri = "https://nid.naver.com/oauth2.0/token";

        // 요청 헤더 설정
        HttpHeaders headers = new HttpHeaders();

        // 요청 바디에 파라미터 세팅
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("client_id", requestParams.get("client_id"));
        params.add("client_secret", requestParams.get("client_secret"));
        params.add("grant_type", "authorization_code");
        params.add("state", requestParams.get("state"));
        params.add("code", requestParams.get("code"));

        // 네이버 인증서버로 POST 요청 날리기
        RestTemplate template = new RestTemplate();

        // 헤더 정보와 파라미터를 하나로 묶기
        HttpEntity<Object> requestEntity = new HttpEntity<>(params, headers);

        ResponseEntity<Map> responseEntity
                = template.exchange(requestUri, HttpMethod.POST, requestEntity, Map.class);

        // 응답 데이터에서 JSON 추출
        Map<String, Object> responseJSON = (Map<String, Object>) responseEntity.getBody();
        log.info("응답 JSON 데이터: {}", responseJSON);

        // access token 추출
        String accessToken = (String) responseJSON.get("access_token");

        return accessToken;

    }
}
