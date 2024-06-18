package com.example.demo.userapi.util;

import jakarta.annotation.PostConstruct;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import net.nurigo.sdk.NurigoApp;
import net.nurigo.sdk.message.request.SingleMessageSendingRequest;
import net.nurigo.sdk.message.response.SingleMessageSentResponse;
import net.nurigo.sdk.message.service.DefaultMessageService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;


import java.util.Random;

@Component
@NoArgsConstructor
@AllArgsConstructor
public class SmsUtil {

    @Value("${coolsms.api.key}")
    private String apiKey;

    @Value("${coolsms.api.secret}")
    private String apiSecretKey;

    private DefaultMessageService messageService;

    @PostConstruct
    private void init() {
        this.messageService = NurigoApp.INSTANCE.initialize(apiKey, apiSecretKey, "https://api.coolsms.co.kr");
    }

    // 단일 메시지 발송 메서드
    public SingleMessageSentResponse sendOne(String to, String verificationCode) {
        net.nurigo.sdk.message.model.Message message = new net.nurigo.sdk.message.model.Message();
        message.setFrom("01092097196");  // 발신번호 설정
        message.setTo(to);  // 수신번호 설정
        message.setText("[EcoCharge] 아래의 인증번호를 입력해주세요\n" + verificationCode);

        try {
            SingleMessageSendingRequest request = new SingleMessageSendingRequest(message);
            SingleMessageSentResponse response = this.messageService.sendOne(request);
            return response;
        } catch (Exception e) {
            // 예외 처리
            e.printStackTrace();
            throw new RuntimeException("SMS 발송 중 오류 발생", e);
        }
    }

}
