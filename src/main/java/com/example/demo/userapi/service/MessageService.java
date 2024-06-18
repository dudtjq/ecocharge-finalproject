package com.example.demo.userapi.service;

import com.example.demo.userapi.util.SmsUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.nurigo.sdk.NurigoApp;
import net.nurigo.sdk.message.model.Message;
import net.nurigo.sdk.message.request.SingleMessageSendingRequest;
import net.nurigo.sdk.message.response.SingleMessageSentResponse;
import net.nurigo.sdk.message.service.DefaultMessageService;
import org.springframework.beans.factory.annotation.Value;
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

    private final Map<String, String> verificationCodeMap = new HashMap<>(); // 인증 코드 저장 맵


    public SingleMessageSentResponse sendSMS(String phoneNumber) {
        String verificationCode = generateVerificationCode();

        // SMS 전송
        return smsUtil.sendOne(phoneNumber, verificationCode);
    }

    public boolean verifyCode(String phoneNumber, String verificationCodeInput) {
        String verificationCode = verificationCodeMap.get(phoneNumber); // 저장된 인증 코드 가져오기

        if (verificationCode == null) {
            return false; // 인증 코드가 존재하지 않으면 false 반환
        }

        // 입력된 인증 코드와 저장된 인증 코드 비교
        return verificationCodeInput.equals(verificationCode);
    }

    private String generateVerificationCode() {
        Random random = new Random();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 4; i++) {
            sb.append(random.nextInt(10));
        }
        return sb.toString();
    }
}
