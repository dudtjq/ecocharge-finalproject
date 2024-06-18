package com.example.demo.userapi.usercontroller;

import com.example.demo.userapi.service.MessageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.nurigo.sdk.message.response.SingleMessageSentResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Slf4j
public class MessageController {

    private final MessageService messageService;

    @PostMapping("/api/send-sms")
    public ResponseEntity<?> sendSMS(@RequestParam String phoneNumber) {
        SingleMessageSentResponse response = messageService.sendSMS(phoneNumber);
        log.info("phoneNumber: {}", phoneNumber);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/api/verify-code")
    public boolean verifyCode(@RequestParam String phoneNumber,
                              @RequestParam String verificationCodeInput) {
        return messageService.verifyCode(phoneNumber, verificationCodeInput);
    }
}
