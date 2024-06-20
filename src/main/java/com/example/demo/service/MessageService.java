package com.example.demo.service;

import com.example.demo.dto.request.MessageRequestDTO;
import com.example.demo.entity.User;
import com.example.demo.repository.UserRepository;
import com.example.demo.util.SmsUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.nurigo.sdk.message.response.SingleMessageSentResponse;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class MessageService {

    private final SmsUtil smsUtil;
    private final UserRepository userRepository;
    private final Map<String, String> verificationCodeMap = new HashMap<>(); // 인증 코드 저장 맵

// SingleMessageSentResponse
    public String sendSms(String phoneNumber) {
        log.info("서비스단 확인:{}",phoneNumber);
        String verificationCode = generateVerificationCode();
        verificationCodeMap.remove(phoneNumber);
        verificationCodeMap.put(phoneNumber,verificationCode);

        log.info("map확인{}",verificationCodeMap.get(phoneNumber));
//        SingleMessageSentResponse response = smsUtil.sendOne(phoneNumber, verificationCode);
        String response = verificationCode;
        // SMS 전송
        return response;
    }


    public Boolean verifyCode(String phoneNumber, String verificationCodeInput) {
        try {
            log.info("verificationcode 확인:{}", verificationCodeMap.get(phoneNumber));
            String verificationCode = verificationCodeMap.get(phoneNumber); // 저장된 인증 코드 가져오기

            if (verificationCode == null) {
                log.info("인증 코드가 null입니다.");
                return false;
            }

            if (verificationCodeInput.equals(verificationCode)) {
                verificationCodeMap.remove(phoneNumber);
                log.info("인증 코드가 일치합니다. 맵에서 제거됨.");
                return true;
            } else {
                log.info("인증 코드가 일치하지 않습니다.");
                return false;
            }
        } catch (Exception e) {
            log.error("인증 코드 확인 중 오류 발생", e);
            throw new RuntimeException("인증 코드 확인 중 오류 발생", e);
        }
    }


    private String generateVerificationCode() {
        Random random = new Random();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 4; i++) {
            sb.append(random.nextInt(10));
        }
        return sb.toString();
    }

    public MessageRequestDTO savePhoneNumber(String phoneNumber) {
        // 전화번호 저장 로직
        User user = new User();
        user.setPhoneNumber(phoneNumber);
        userRepository.save(user);
        return null;
    }
}
