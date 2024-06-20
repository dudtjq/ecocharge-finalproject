package com.example.demo.api;

import com.example.demo.dto.request.MessageRequestDTO;
import com.example.demo.service.MessageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.nurigo.sdk.message.response.SingleMessageSentResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Slf4j
public class MessageController {

    private final MessageService messageService;

    @PostMapping("/api/send-sms")
    public ResponseEntity<?> sendMessage(@RequestBody MessageRequestDTO dto) {
        log.info("controller단에 요청이 들어옴");
//        SingleMessageSentResponse response = messageService.sendSms(dto.getPhoneNumber());
        String response = String.valueOf(messageService.sendSms(dto.getPhoneNumber()));
//        log.info("phoneNumber: {}", phoneNumber);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/api/verify-code")
    public boolean verifyCode(@RequestBody MessageRequestDTO request) {
        String phoneNumber = request.getPhoneNumber();
        String verificationCodeInput = request.getVerifivationCodeInput();
        log.info("서비스단 확인:{}", verificationCodeInput);
        boolean response = messageService.verifyCode(phoneNumber, verificationCodeInput);
        log.info("reseponse의 결과값: {} ",response);
        return  response;
    }

    @PostMapping("/api/save-phone")
    public ResponseEntity<?> savePhoneNumber(@RequestBody MessageRequestDTO phoneNumber) {
        log.info("save-phone 확인 phoneNumber: {}", phoneNumber);
//        MessageRequestDTO response=  messageService.savePhoneNumber(phoneNumber.getPhoneNumber());
        MessageRequestDTO response= phoneNumber;
        log.info("phoneNumber확인: {}",response);
        return ResponseEntity.ok("Phone number saved successfully");
    }
    
}
