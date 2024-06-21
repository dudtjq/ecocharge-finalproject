package com.example.demo.service;

import com.example.demo.auth.TokenProvider;
import com.example.demo.auth.TokenUserInfo;
import com.example.demo.dto.response.LoginResponseDTO;
import com.example.demo.entity.User;
import com.example.demo.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.*;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class UserService {

    @Autowired
    private final UserRepository userRepository;
    private final TokenProvider tokenProvider;

    @Value("${upload.path}")
    private String uploadRootPath;

    // 나중에 폰번호로 조회하면서 삭제할 예정
    public boolean isDuplicate(String email) {
        if (userRepository.existsByEmail(email)) {
            log.warn("이메일이 중복되었습니다. - {}", email);
            return true;
        } else return false;
    }

    public LoginResponseDTO authenticate(final LoginResponseDTO dto) {
        User user = userRepository.findByEmail(dto.getEmail())
                .orElseThrow(() -> new RuntimeException("존재하지 않는 계정입니다."));

        log.info("{}님 로그인 성공!", user.getUserName());

        Map<String, String> token = getTokenMap(user);

        user.changeAccessToken(token.get("access_token"));
        user.changeRefreshToken(token.get("refresh_token"));
        user.changeRefreshExpiryDate(tokenProvider.getExpiryDate(token.get("refresh_token")));
        userRepository.save(user);

        return new LoginResponseDTO(user, token);
    }

    protected Map<String, String> getTokenMap(User user) {

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

    public String findProfilePath(String email) {
        User user = userRepository.findByEmail(email).orElseThrow(() -> new RuntimeException());
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
                String accessToken = foundUser.getAccessToken();
                logoutUrl = "https://accounts.google.com/o/oauth2/revoke?token=" + accessToken;
                ResponseEntity<String> response = restTemplate.postForEntity(logoutUrl, null, String.class);
                if (response.getStatusCode() == HttpStatus.OK) {
                    log.info("Google logout successful for user: {}", userInfo.getEmail());
                } else {
                    log.error("Google logout failed for user: {}", userInfo.getEmail());
                }
            } else if (foundUser.getLoginMethod() == User.LoginMethod.KAKAO) {
                String accessToken = foundUser.getAccessToken();
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
                String accessToken = foundUser.getAccessToken();
                headers.add("Authorization", "Bearer " + accessToken);
                HttpEntity<String> entity = new HttpEntity<>(headers);
                logoutUrl = "https://nid.naver.com/oauth2.0/token?grant_type=delete&access_token=" + accessToken;
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

//
//
//    public ResponseEntity<?> sendSmsToFindEmail(LoginResponseDTO responseDTO) {
//        String name = responseDTO.getUserName();
//        //수신번호 형태에 맞춰 "-"을 ""로 변환
//        String phoneNum = responseDTO.getPhoneNumber().replaceAll("-","");
//
//        User foundUser = userRepository.findByphoneNumber( phoneNum).orElseThrow(()->
//                new NoSuchElementException("회원이 존재하지 않습니다."));
//
//        String receiverEmail = foundUser.getEmail();
//        String verificationCode = smsUtil.createCode();
//        smsUtil.sendOne(phoneNum, verificationCode);
//
//        //인증코드 유효기간 5분 설정
////        redisUtil.setDataExpire(verificationCode, receiverEmail, 60 * 5L);
//
//        return ResponseEntity.ok(new Message());
//    }
//


}
