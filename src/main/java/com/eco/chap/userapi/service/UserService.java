package com.eco.chap.userapi.service;

import com.eco.chap.auth.TokenProvider;
import com.eco.chap.auth.TokenUserInfo;
import com.eco.chap.dto.request.LoginRequestDTO;
import com.eco.chap.dto.response.LoginResponseDTO;
import com.eco.chap.userapi.entity.User;
import com.eco.chap.userapi.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class UserService {

    private final UserRepository userRepository;
    private final TokenProvider tokenProvider;

    @Value("${upload.path}")
    private String uploadRootPath;


    public LoginResponseDTO authenticate(final LoginRequestDTO dto) {
        User user = userRepository.findByPhoneNumber(dto.getPhoneNumber())
                .orElseThrow(() -> new RuntimeException("존재하지 않는 계정입니다."));

        log.info("{}님 로그인 성공!", user.getUserName());

        Map<String, String> token = getTokenMap(user);

        user.changeRefreshToken(token.get("refresh_token"));
        user.changeRefreshExpiryDate(tokenProvider.getExpiryDate(token.get("refresh_token")));
        userRepository.save(user);

        return new LoginResponseDTO(user, token);
    }

    private Map<String, String> getTokenMap(User user) {
        String accessToken = tokenProvider.createAccessKey(user);
        String refreshToken = tokenProvider.createRefreshKey(user);

        Map<String, String> token = new HashMap<>();
        token.put("access_token", accessToken);
        token.put("refresh_token", refreshToken);
        return token;
    }

    public String uploadProfileImage(MultipartFile profileImage) throws IOException {
        File rootDir = new File(uploadRootPath);
        if (!rootDir.exists()) rootDir.mkdirs();

        String uniqueFileName = UUID.randomUUID() + "_" + profileImage.getOriginalFilename();
        File uploadFile = new File(uploadRootPath + "/" + uniqueFileName);
        profileImage.transferTo(uploadFile);

        return uniqueFileName;
    }

    public String findProfilePath(String userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException());
        String profileImg = user.getProfileImg();
        if (profileImg.startsWith("http://")) {
            return profileImg;
        }
        return uploadRootPath + "/" + profileImg;
    }

    public String logout(TokenUserInfo userInfo) {
        User foundUser = userRepository.findByEmail(userInfo.getEmail())
                .orElseThrow();

        HttpHeaders headers = new HttpHeaders();
        RestTemplate restTemplate = new RestTemplate();
        String logoutUrl = null;

        try {
            if (foundUser.getLoginMethod() == User.LoginMethod.GOOGLE) {
                String accessToken = foundUser.getGoogleAccessToken();
                logoutUrl = "https://accounts.google.com/o/oauth2/revoke?token=" + accessToken;
                ResponseEntity<String> response = restTemplate.postForEntity(logoutUrl, null, String.class);
                if (response.getStatusCode() == HttpStatus.OK) {
                    log.info("Google logout successful for user: {}", userInfo.getEmail());
                } else {
                    log.error("Google logout failed for user: {}", userInfo.getEmail());
                }
            } else if (foundUser.getLoginMethod() == User.LoginMethod.KAKAO) {
                String accessToken = foundUser.getKakaoAccessToken();
                headers.add("Authorization", "Bearer " + accessToken);
                HttpEntity<String> entity = new HttpEntity<>(headers);
                logoutUrl = "https://kapi.kakao.com/v1/user/logout";
                ResponseEntity<String> response = restTemplate.postForEntity(logoutUrl, entity, String.class);
                if (response.getStatusCode() == HttpStatus.OK) {
                    log.info("Kakao logout successful for user: {}", userInfo.getEmail());
                } else {
                    log.error("Kakao logout failed for user: {}", userInfo.getEmail());
                }
            } else if (foundUser.getLoginMethod() == User.LoginMethod.NAVER) {
                String accessToken = foundUser.getNaverAccessToken();
                headers.add("Authorization", "Bearer " + accessToken);
                HttpEntity<String> entity = new HttpEntity<>(headers);
                logoutUrl = "https://nid.naver.com/oauth2.0/token?grant_type=delete&client_id=YOUR_CLIENT_ID&client" +
                        "_secret=YOUR_CLIENT_SECRET&access_token=" + accessToken + "&service_provider=NAVER";
                ResponseEntity<String> response = restTemplate.exchange(logoutUrl, HttpMethod.GET, entity, String.class);
                if (response.getStatusCode() == HttpStatus.OK) {
                    log.info("Naver logout successful for user: {}", userInfo.getEmail());
                } else {
                    log.error("Naver logout failed for user: {}", userInfo.getEmail());
                }
            }
        } catch (Exception e) {
            log.error("Logout failed for user: {}", userInfo.getEmail(), e);
        }

        return "Logout successful";
    }
    public  void test(){
        return;
    }

    public String renewalAccessToken(Map<String, String> tokenRequest) {
        String refreshToken = tokenRequest.get("refreshToken");
        boolean isValid = tokenProvider.validateRefreshToken(refreshToken);  // tokenProvider 추가
        if (isValid) {
            User foundUser = userRepository.findByRefreshToken(refreshToken).orElseThrow();
            if (!foundUser.getRefreshTokenExpiryDate().before(new Date())) {
                String newAccessKey = tokenProvider.createAccessKey(foundUser);
                return newAccessKey;
            }
        }
        return null;
    }
}
