package com.example.demo.api;

import com.example.demo.dto.request.MessageRequestDTO;
import com.example.demo.service.MessageService;
import com.example.demo.service.UserService;
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
    private final UserService userService;

    @PostMapping("/api/send-sms")
    public ResponseEntity<?> sendMessage(@RequestBody MessageRequestDTO dto) {
        log.info("controller단에 요청이 들어옴");
//        SingleMessageSentResponse response = messageService.sendSms(dto.getPhoneNumber());
        String response = String.valueOf(messageService.sendSms(dto.getPhoneNumber()));
        String phone = "ECO" + dto.getPhoneNumber();
        Boolean duplicatePhone = userService.isDuplicatePhone(phone);
        if(duplicatePhone){
          return ResponseEntity.badRequest().body(duplicatePhone);
        }
//        log.info("phoneNumber: {}", phoneNumber);
        return ResponseEntity.ok().body(response);
    }

    @PostMapping("/api/verify-code")
    public boolean verifyCode(@RequestBody MessageRequestDTO request) {
        log.info("request확인: {}",request);
        String phoneNumber = request.getPhoneNumber();
        String verificationCodeInput = request.getVerificationCodeInput();
        log.info("서비스단 확인:{}", verificationCodeInput);
        boolean response = messageService.verifyCode(phoneNumber, verificationCodeInput);
        log.info("reseponse의 결과값: {} ",response);
        return  response;
    }


}
