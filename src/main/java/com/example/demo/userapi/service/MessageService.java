package com.example.demo.userapi.service;

import com.example.demo.userapi.util.SmsUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;

@Service
@RequiredArgsConstructor
public class MessageService {

    private final SmsUtil smsUtil;
    private final Map<String, String> verificationCodes = new ConcurrentHashMap<>();

    public void sendVerificationCode(String phoneNumber) {
        String verificationCode = createVerificationCode();
        smsUtil.sendOne(phoneNumber, verificationCode);
        verificationCodes.put(phoneNumber, verificationCode);

        // 코드 유효 기간 설정 (5분 후 만료)
        new Thread(() -> {
            try {
                Thread.sleep(5 * 60 * 1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            verificationCodes.remove(phoneNumber);
        }).start();
    }

    public boolean verifyCode(String phoneNumber, String verificationCode) {
        String storedCode = verificationCodes.get(phoneNumber);
        return verificationCode.equals(storedCode);
    }

    private String createVerificationCode() {
        Random random = new Random();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 4; i++) {
            sb.append(random.nextInt(10));
        }
        return sb.toString();
    }
}
