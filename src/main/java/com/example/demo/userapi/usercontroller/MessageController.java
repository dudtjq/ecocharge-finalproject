package com.example.demo.userapi.usercontroller;

import com.example.demo.userapi.service.MessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/sms")
@RequiredArgsConstructor
public class MessageController {

    private final MessageService messageService;

    @PostMapping("/send")
    public ResponseEntity<?> sendVerificationCode(@RequestParam String phoneNumber) {
        messageService.sendVerificationCode(phoneNumber);
        return ResponseEntity.ok("인증 코드가 발송되었습니다.");
    }

    @PostMapping("/verify")
    public ResponseEntity<?> verifyCode(@RequestParam String phoneNumber, @RequestParam String verificationCode) {
        boolean isVerified = messageService.verifyCode(phoneNumber, verificationCode);
        if (isVerified) {
            return ResponseEntity.ok("인증에 성공했습니다.");
        } else {
            return ResponseEntity.status(400).body("인증에 실패했습니다.");
        }
    }
}
