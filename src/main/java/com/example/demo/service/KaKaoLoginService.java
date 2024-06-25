package com.example.demo.service;

import com.example.demo.entity.User;
import com.example.demo.dto.response.KakaoUserResponseDTO;
import com.example.demo.dto.response.LoginResponseDTO;
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
public class KaKaoLoginService {

    private final UserRepository userRepository;
    private final UserService userService;

    @Value("${kakao.client_id}")
    private String KAKAO_CLIENT_ID;
    @Value("${kakao.redirect_url}")
    private String KAKAO_REDIRECT_URL;
    @Value("${kakao.client_secret}")
    private String KAKAO_CLIENT_SECRET;

    public LoginResponseDTO kakaoService(String code ,String phoneNumber) {
        // 인가 코드를 통해 토큰을 발급받기
        String accessToken = getKakaoAccessToken(code);
        log.info("token: {}", accessToken);

        // 토큰을 통해 사용자 정보를 가져오기
        KakaoUserResponseDTO userDTO = getKakaoUserInfo(accessToken);
        log.info("userDTO: {}", userDTO);

        // 일회성 로그인으로 처리 ->  dto를 바로 화면단에 리턴
        // 회원가입 처리 -> 이메일 중복 검사 진행 -> 자체 jwt를 생성해서 토큰을 화면단에 리턴
        // -> 화면단에서는 적절한 url을 선택하여 redirect를 진행

        if (!userService.isDuplicatePhone(userDTO.getKakaoAccount().getPhoneNumber())){
            userRepository.save(userDTO.toEntity(accessToken,phoneNumber));
        }
        // 이메일이 중복됐다? -> 이전에 로그인 한 적이 있다. -> DB에 데이터를 또 넣을 필요는 없다.
        log.info("phoneNumber: {}", phoneNumber);
        User foundUser
                = userRepository.findByPhoneNumber(userDTO.getKakaoAccount().getPhoneNumber().describeConstable().orElseThrow());
        // 우리 사이트에서 사용하는 jwt 생성
        Map<String, String> token = userService.getTokenMap(foundUser);

        foundUser.changeAccessToken(accessToken);
        userRepository.save(foundUser);

        return new LoginResponseDTO(foundUser, token);

    }

    private KakaoUserResponseDTO getKakaoUserInfo(String accessToken) {
        // 요청 uri
        String requestURI = "https://kapi.kakao.com/v2/user/me";

        // 요청 헤더 설정
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + accessToken);
        headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");

        // 요청 보내기
        RestTemplate template = new RestTemplate();
        ResponseEntity<KakaoUserResponseDTO> responseEntity
                = template.exchange(requestURI, HttpMethod.GET, new HttpEntity<>(headers), KakaoUserResponseDTO.class);

        // 응답 바디 꺼내기
        KakaoUserResponseDTO responseData = responseEntity.getBody();
        log.info("user profile: {}", responseData);

        return responseData;
    }

    private String getKakaoAccessToken(String code) {

        // 요청 uri
        String requestURI = "https://kauth.kakao.com/oauth/token";

        // 요청 헤더 설정
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");

        // 요청 바디(파라미터) 설정
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("grant_type", "authorization_code"); // 카카오 공식 문서 기준 값으로 세팅
        params.add("client_id", KAKAO_CLIENT_ID); // 카카오 디벨로퍼 REST API 키
        params.add("redirect_uri", KAKAO_REDIRECT_URL); // 카카오 디벨로퍼 등록된 redirect uri
        params.add("code", code); // 프론트에서 인가 코드 요청시 전달받은 코드값
        params.add("client_secret", KAKAO_CLIENT_SECRET); // 카카오 디벨로퍼 client secret(활성화 시 추가해 줘야 함)

        // 헤더와 바디 정보를 합치기 위해 HttpEntity 객체 생성
        HttpEntity<Object> requestEntity = new HttpEntity<>(params, headers);

        // 카카오 서버로 POST 통신
        RestTemplate template = new RestTemplate();

        // 통신을 보내면서 응답데이터를 리턴
        // param1: 요청 url
        // param2: 요청 메서드 (전송 방식)
        // param3: 헤더와 요청 파라미터정보 엔터티
        // param4: 응답 데이터를 받을 객체의 타입 (ex: dto, map)
        // 만약 구조가 복잡한 경우에는 응답 데이터 타입을 String으로 받아서 JSON-simple 라이브러리로 직접 해체.
        ResponseEntity<Map> responseEntity
                = template.exchange(requestURI, HttpMethod.POST, requestEntity, Map.class);

        /*
        HTTP/1.1 200 OK
        Content-Type: application/json;charset=UTF-8
        {
            "token_type":"bearer",
            "access_token":"${ACCESS_TOKEN}",
            "expires_in":43199,
            "refresh_token":"${REFRESH_TOKEN}",
            "refresh_token_expires_in":5184000,
            "scope":"account_email profile"
        }
         */

        // 응답 데이터에서 필요한 정보를 가져오기
        Map<String, Object> responseData = (Map<String, Object>) responseEntity.getBody();
        log.info("토큰 요청 응답 데이터: {}", responseData);

        // 여러가지 데이터 중 access_token이라는 이름의 데이터를 리턴
        // Object를 String으로 형 변환해서 리턴
        return (String) responseData.get("access_token");
    }
}