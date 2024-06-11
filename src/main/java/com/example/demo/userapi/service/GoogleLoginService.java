//package com.example.demo.userapi.service;
//
//import jakarta.servlet.http.HttpSession;
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.http.*;
//import org.springframework.stereotype.Service;
//import org.springframework.util.LinkedMultiValueMap;
//import org.springframework.util.MultiValueMap;
//import org.springframework.web.client.RestTemplate;
//
//import java.util.Map;
//
//@Service
//@Slf4j
//@RequiredArgsConstructor
//public class GoogleLoginService {
//
//    private final UserService userService;
//
//    public void googleLogin(Map<String, String> params, HttpSession session) {
//
//        String accessToken = getGoogleAccessToken(params);
//        log.info("access_token: {}", accessToken);
//
//        // 전달받은 엑세스 토큰을 활용하여 사용자의 정보 가져오기
//        GoogleUserResponseDTO dto = getGoogleUserInfo(accessToken);
//
//        // 구글에서 받은 회원정보로 우리 사이트 회원가입
//        // 구글은 아이디를 제공하지 않아 email + google 로 임의 아이디 생성
//        String id = dto.getEmail() + "google" ;
//        log.info("구글 소셜 아이디: {}", id);
//
//        // 회원 중복 확인 (아이디)
//        if (!userService.checkDuplicateValue("account", id)) {
//            // 한 번도 구글 로그인을 한 적이 없다면 회원가입 진행
//            userService.join(
//                    SignUpRequestDto.builder()
//                            .accountNumber(id)
//                            .password("0000")
//                            .email(dto.getEmail())
//                            .name(dto.getName())
//                            .loginMethod(User.LoginMethod.GOOGLE)
//                            .build(),
//                    dto.getPicture()
//            );
//        }
//
////         우리 사이트 로그인 처리
//        userService.maintainLoginState(session, id);
//
//    }
//
//    private GoogleUserResponseDTO getGoogleUserInfo(String accessToken) {
//
//        String userInfoUri = "https://www.googleapis.com/oauth2/v3/userinfo";
//
//        HttpHeaders headers = new HttpHeaders();
//        headers.add("Authorization", "Bearer " + accessToken);
//
//        RestTemplate template = new RestTemplate();
//
//        ResponseEntity<GoogleUserResponseDTO> responseEntity = template.exchange(
//                userInfoUri,
//                HttpMethod.GET,
//                new HttpEntity<>(headers),
//                GoogleUserResponseDTO.class
//        );
//
//        GoogleUserResponseDTO responseJSON = responseEntity.getBody();
//        log.info("응답 데이터 결과 : {}", responseJSON);
//
//        return responseJSON;
//
//    }
//
//    private String getGoogleAccessToken(Map<String, String> requestParams) {
//
//        // 요청 URI
//        String requestUri = "https://oauth2.googleapis.com/token";
//
//        // 요청 헤더 설정
//        HttpHeaders headers = new HttpHeaders();
//        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
//
//        // 요청 바디에 파라미터 세팅
//        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
//        params.add("code", requestParams.get("code"));
//        params.add("client_id", requestParams.get("client_id"));
//        params.add("client_secret", requestParams.get("client_secret"));
//        params.add("redirect_uri", requestParams.get("redirect_uri"));
//        params.add("grant_type", "authorization_code");
//
//        // REST API를 호출하기 위한 RestTemplate 객체 생성
//        RestTemplate template = new RestTemplate();
//
//        // 헤더 정보와 파라미터를 하나로 묶기
//        HttpEntity<Object> requestEntity = new HttpEntity<>(params, headers);
//
//        ResponseEntity<Map> responseEntity
//                = template.exchange(requestUri, HttpMethod.POST, requestEntity, Map.class);
//
//        // 응답 데이터에서 JSON 추출
//        Map<String, Object> responseJSON = (Map<String, Object>) responseEntity.getBody();
//        log.info("응답 JSON 데이터: {}", responseJSON);
//
//        // access token 추출
//        String accessToken = (String) responseJSON.get("access_token");
//
//        return accessToken;
//    }
//}
