package com.example.demo.service;
import com.example.demo.auth.TokenProvider;
import com.example.demo.dto.response.KakaoUserResponseDTO;
import com.example.demo.dto.response.LoginResponseDTO;
import com.example.demo.entity.User;
import com.example.demo.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class SnsLoginService {

    private final UserRepository userRepository;
    private final TokenProvider tokenProvider;

    @Value("${kakao.client_id}")
    private String KAKAO_CLIENT_ID;

    @Value("${kakao.redirect_url}")
    private String KAKAO_REDIRECT_URL;

    @Value("${kakao.client_secret}")
    private String KAKAO_CLIENT_SECRET;



    //카카오
    public LoginResponseDTO kakaoService(String code) {

        // 인가 코드를 통해 토큰을 발급받기
        String accessToken = getKakaoAccessToken(code);
        log.info("token: {}", accessToken);

        // 토큰을 통해 사용자 정보를 가져오기
        KakaoUserResponseDTO userDTO = getKakaoUserInfo(accessToken);
        log.info("userDTO: {}", userDTO);

        // 이메일 중복 검사
        if (!isDuplicate(userDTO.getKakaoAccount().getEmail())) {
            // 이메일이 중복되지 않으면 회원가입 처리
            User saved = userRepository.save(userDTO.toEntity(accessToken));
        }

        // 중복된 이메일의 사용자를 찾기
        User foundUser = userRepository.findByEmail(userDTO.getKakaoAccount().getEmail()).orElseThrow();

        // 우리 사이트에서 사용하는 jwt를 생성
        Map<String, String> token = getTokenMap(foundUser);

        // 기존에 로그인했던 사용자의 access token값을 update
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
        ResponseEntity<KakaoUserResponseDTO> responseEntity = template.exchange(requestURI, HttpMethod.GET, new HttpEntity<>(headers), KakaoUserResponseDTO.class);

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

        // 통신을 보내면서 응답 데이터를 리턴
        ResponseEntity<Map> responseEntity = template.exchange(requestURI, HttpMethod.POST, requestEntity, Map.class);

        // 응답 데이터에서 필요한 정보를 가져오기
        Map<String, Object> responseData = (Map<String, Object>) responseEntity.getBody();
        log.info("토큰 요청 응답 데이터:{}", responseData);

        // 여러가지 데이터 중 access_token이라는 이름의 데이터를 리턴
        return (String) responseData.get("access_token");
    }

    private boolean isDuplicate(String email) {
        if (userRepository.existsByEmail(email)) {
            log.warn("이메일이 중복되었습니다. - {}", email);
            return true;
        } else return false;
    }

    private Map<String, String> getTokenMap(User user) {
        String accessToken = tokenProvider.createAccessKey(user);
        String refreshToken = tokenProvider.createRefreshKey(user);

        Map<String, String> token = new HashMap<>();
        token.put("access_token", accessToken);
        token.put("refresh_token", refreshToken);
        return token;
    }

    //네이버
//    public void naverLogin(Map<String, String> naverParams , HttpSession session) {
//
//        String accessToken = getNaverToken(naverParams);
//        session.setAttribute("access_token", accessToken);
//        log.info("access_token: {}",accessToken);
//
//        NaverResponseDTO dto = getNaverUserInfo(accessToken);
//
//        //네이버에서받은정보로 회원가입
//        String email = dto.getResponse().getEmail();
//        log.info("이메일: {}",email);
//        if (!memberService.checkDuplicateValue("email",email)) {
//            memberService.snsJoin(NaverSignUpRequestDTO.builder()
//                            .email(email)
//                            .password("0000")
//                            .nickname(dto.getResponse().getNickname())
//                            .loginMethod(Member.LoginMethod.NAVER)
//                            .build(),
//                    dto.getResponse().getProfileImage()
//            );
//
//        }
//
//        memberService.maintainLoginState(session,email);
//
//    }
//
//    //토큰발급
//    public String getNaverToken(Map<String, String> naverParams) {
//        String getTokenUri = "https://nid.naver.com/oauth2.0/token";
//        MultiValueMap<String ,String> tokenParams = new LinkedMultiValueMap<>();
//        tokenParams.add("grant_type" , "authorization_code");
//        tokenParams.add("client_id",naverParams.get("client_id"));
//        tokenParams.add("client_secret",naverParams.get("client_secret"));
//        tokenParams.add("code",naverParams.get("code"));
//        tokenParams.add("state",naverParams.get("state"));
//        log.info("tokenParams : {}",tokenParams);
//
//        RestTemplate template = new RestTemplate();
//        HttpHeaders headers = new HttpHeaders();
//
//        HttpEntity<Object> requestEntity = new HttpEntity<>(tokenParams, headers);
//        ResponseEntity<Map> responseEntity
//                = template.exchange(getTokenUri, HttpMethod.POST, requestEntity, Map.class);
//
//        Map<String, Object> responseJSON = (Map<String, Object>) responseEntity.getBody();
//        log.info("응답 JSON 데이터: {}", responseJSON);
//
//        String accessToken = (String) responseJSON.get("access_token");
//
//        return accessToken;
//
//    }
//
//    //유저 정보가져오기
//    private NaverResponseDTO getNaverUserInfo(String accessToken){
//        String RequestUrl = "https://openapi.naver.com/v1/nid/me";
//        HttpHeaders headers = new HttpHeaders();
//        headers.add("Authorization", "Bearer " + accessToken);
//
//        RestTemplate template = new RestTemplate();
//        ResponseEntity<NaverResponseDTO> responseEntity = template.exchange(
//                RequestUrl, HttpMethod.POST,new HttpEntity<>(headers), NaverResponseDTO.class
//        );
//        NaverResponseDTO responseJSON = responseEntity.getBody();
//        log.info("응답JSON 데이터(유저정보): {}", responseJSON);
//
//        return responseJSON;
//    }






}
