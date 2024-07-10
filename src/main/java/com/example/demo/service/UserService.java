package com.example.demo.service;

import com.example.demo.auth.TokenProvider;
import com.example.demo.auth.TokenUserInfo;
import com.example.demo.dto.request.LoginRequestDTO;
import com.example.demo.dto.request.UserSignUpRequestDTO;
import com.example.demo.dto.request.ModifyUserRequestDTO;
import com.example.demo.dto.response.LoginResponseDTO;
import com.example.demo.dto.response.ModifyUserResponseDTO;
import com.example.demo.dto.response.UserResponseDTO;
import com.example.demo.dto.response.UserSignUpResponseDTO;
import com.example.demo.entity.User;
import com.example.demo.repository.UserRepository;
import com.example.demo.util.SmsUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.security.crypto.password.PasswordEncoder;
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
    private  final PasswordEncoder passwordEncoder;

    private MessageService messageService;

    @Value("${upload.path}")
    private String uploadRootPath;
    @Autowired
    private SmsUtil smsUtil;


    public Boolean isDuplicatePhone(String phoneNumber) {
        log.info("isDuplicatePhone(phoneNumber): {}", phoneNumber);
        if (userRepository.existsByPhoneNumber(phoneNumber)) {
            log.warn(" 전화번호가 중복되었습니다. - {}", phoneNumber);
            return true;
        } else return false;
    }

    public boolean isDuplicateIdentify(String identify) {
        if (userRepository.existsByidentify(identify.replace("\"", ""))) {
            log.warn(" ID가 중복되었습니다. - {}", identify);
            return true;
        } else return false;
    }
    public Boolean isDuplicateId(String Identify) {
        if (userRepository.findByIdentify(Identify) != null) {
            log.warn(" 아이디가 중복되었습니다.. - {}", Identify);
            return true;
        } else return false;
    }

//    public void findByPw(String phoneNumber, String verificationCode){
//        smsUtil.sendOne(phoneNumber,verificationCode);
//        userRepository.ChangePw(phoneNumber,verificationCode);
//    }

    public LoginResponseDTO authenticate(final LoginRequestDTO dto) {
        log.info("authenticate 동작, dto={}", dto);
        User user = userRepository.findByIdentify(dto.getId());
        log.info("user={}", user);
        // 패스워드 검증
        String rawPassword = dto.getPassword();
        log.info("rawPassword={}", rawPassword);
        if (rawPassword.trim().equals("")) {
            log.info("비밀번호는 null");
            throw new IllegalArgumentException("비밀번호를 입력해주세요.");
        }
        // 입력한 비번
        String encodedPassword = user.getPassword(); // DB에 저장된 암호화된 비번
        log.info("encodedPassword={}", encodedPassword);

        if (!passwordEncoder.matches(rawPassword.trim(), encodedPassword.trim())) {
            log.info("hi");
            throw new RuntimeException("비밀번호가 틀렸습니다.");
        }
        log.info("{}님 로그인 성공!", user.getUserName());
        log.info("권한:{}",user.getRole());

        Map<String, String> token = getTokenMap(user);

        user.changeRefreshToken(token.get("refresh_token"));
        user.changeAccessToken(token.get("access_token"));
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
        User foundUser = userRepository.findById(userInfo.getUserId()).orElseThrow();
        log.info("foundUser: {}", foundUser);

        String accessToken = foundUser.getAccessToken();
        HttpHeaders headers = new HttpHeaders();
        RestTemplate restTemplate = new RestTemplate();
        String logoutUrl = null;

        if (accessToken != null) {
            try {
                if (foundUser.getLoginMethod()== User.LoginMethod.GOOGLE) {
                    logoutUrl = "https://accounts.google.com/o/oauth2/revoke?token=" + accessToken;
                    ResponseEntity<String> response = restTemplate.postForEntity(logoutUrl, null, String.class);
                    if (response.getStatusCode() == HttpStatus.OK) {
                        log.info("Google logout successful for user: {}", userInfo.getUserId());
                        foundUser.changeAccessToken(null);
                        userRepository.save(foundUser);
                        log.error("Google logout failed for user: {}", userInfo.getUserId());
                    }
                } else if (foundUser.getLoginMethod() == User.LoginMethod.KAKAO) {
                    headers.add("Authorization", "Bearer " + accessToken);
                    HttpEntity<String> entity = new HttpEntity<>(headers);
                    logoutUrl = "https://kapi.kakao.com/v1/user/logout";
                    ResponseEntity<String> response = restTemplate.postForEntity(logoutUrl, entity, String.class);
                    if (response.getStatusCode() == HttpStatus.OK) {
                        log.info("Kakao logout successful for user: {}", userInfo.getUserId());
                        foundUser.changeAccessToken(null);
                        userRepository.save(foundUser);
                    } else {
                        log.error("Kakao logout failed for user: {}", userInfo.getUserId());
                    }

                } else if (foundUser.getLoginMethod() == User.LoginMethod.NAVER) {
                    headers.add("Authorization", "Bearer " + accessToken);
                    HttpEntity<String> entity = new HttpEntity<>(headers);
                    logoutUrl = "https://nid.naver.com/oauth2.0/token?grant_type=delete&access_token=" + accessToken;
                    ResponseEntity<String> response = restTemplate.exchange(logoutUrl, HttpMethod.GET, entity, String.class);
                    if (response.getStatusCode() == HttpStatus.OK) {
                        log.info("Naver logout successful for user: {}", userInfo.getUserId());
                        foundUser.changeAccessToken(null);
                        userRepository.save(foundUser);
                    } else {
                        log.error("Naver logout failed for user: {}", userInfo.getUserId());
                    }
                }
            } catch (Exception e) {
                log.error("Logout failed for user: {}", userInfo.getUserId(), e);
            }
        }

        return null;
    }

    public String renewalAccessToken(Map<String, String> tokenRequest) {
        String refreshToken = tokenRequest.get("refreshToken");
        boolean isValid = tokenProvider.validateRefreshToken(refreshToken);  // tokenProvider 추가
        log.info("isValid: {}", isValid);
        if (isValid) {
            User foundUser = userRepository.findByRefreshToken(refreshToken).orElseThrow();
            if (!foundUser.getRefreshTokenExpiryDate().before(new Date())) {
                String newAccessKey = tokenProvider.createAccessKey(foundUser);
                return newAccessKey;
            }
        }
        return null;
    }

    public UserSignUpResponseDTO create(final UserSignUpRequestDTO dto) {
        String phone = "ECO"+dto.getPhoneNumber();


        if (isDuplicatePhone(phone)) {
            throw new RuntimeException("이미 가입된 회원입니다.");
        }
        if(isDuplicateId(dto.getId())){
            throw new RuntimeException("중복된 ID입니다.");
        }

//         패스워드 인코딩
        String encoded = passwordEncoder.encode(dto.getPassword());
        dto.setPassword(encoded);


        // dto를 User Entity로 변환해서 저장.
        User saved = userRepository.save(dto.toEntity(phone));
        log.info("회원 가입 정상 수행됨! - saved user - {}", saved);

        return new UserSignUpResponseDTO(saved);
    }



    public void changePassword(ModifyUserRequestDTO dto) {
        log.info("dto: {}", dto.getPassword());
        log.info("dto: {}", dto.getPhoneNumber());
        String password = passwordEncoder.encode(dto.getPassword());
        String phoneNumber = "ECO"+dto.getPhoneNumber();

//        User user = userRepository.findByPhoneNumber(phoneNumber);
//        user.setPassword(password);
//        userRepository.save(user);

        userRepository.updatePassword(phoneNumber, password);
    }

    public String showid( String phoneNumber) {
        String number = "ECO" + phoneNumber;
        User user = userRepository.showedId(number);
        return user.getIdentify();
    }

    public UserResponseDTO findUser (String userId) {
        User findUser = userRepository.findById(userId).orElseThrow();
        return new UserResponseDTO(findUser);

    }

    public ModifyUserResponseDTO modifyUserInfo(ModifyUserRequestDTO dto) {
        String savedphoneNumber = dto.getPhoneNumber();

        if (isDuplicatePhone(savedphoneNumber)) {
            throw new RuntimeException("사용중인 핸드폰 번호 입니다");
        }

        if (dto.getLoginMethod().equals(User.LoginMethod.COMMON)) {
            savedphoneNumber = "ECO" + dto.getPhoneNumber();
        }

        log.info("modify phoneNumber: {}", savedphoneNumber);
        log.info("original phoneNumber: {}", dto.getOriginalPhone());

        String findPhone = dto.getOriginalPhone();
        if (dto.getLoginMethod().equals(User.LoginMethod.COMMON)) {
            findPhone = "ECO" + dto.getOriginalPhone();
        }
        log.info("findPhoneNumber: {}", findPhone);
        User foundUser = userRepository.findByPhoneNumber(findPhone);

        String encoded = passwordEncoder.encode(dto.getPassword());
        foundUser.setPassword(encoded);
        foundUser.setUserName(dto.getUserName());
        foundUser.setPhoneNumber(savedphoneNumber);

        log.info("foundUser: {}", foundUser);
        User saved = userRepository.save(foundUser);

        return new ModifyUserResponseDTO(saved);
    }
}
