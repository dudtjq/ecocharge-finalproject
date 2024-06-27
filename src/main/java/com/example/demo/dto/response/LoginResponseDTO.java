package com.example.demo.dto.response;
import com.example.demo.entity.User;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.time.LocalDate;
import java.util.Map;


// 로그인 성공 후 클라이언트에게 전송할 데이터 객체
@Getter @ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LoginResponseDTO {

    private  String id;

    @NotBlank
    private String password;

    @JsonFormat(pattern = "yyyy년 MM월 dd일")
    private LocalDate joinDate;

    private Map<String, String> token; // 인증 토큰 (핵심)

    private String phoneNumber;

    private String role;

    private User.LoginMethod loginMethod;

    private String userName;


    public LoginResponseDTO(User user, Map<String, String> token) {
        this.joinDate = LocalDate.from(user.getJoinDate()); // LocalDateTime 타입이 다르면 from을 이용해서 넣어라!
        this.password=user.getPassword();
        this.loginMethod = user.getLoginMethod();
        this.userName = user.getUserName();
        this.token = token;
        this.phoneNumber= user.getPhoneNumber();
        this.id = getId();
    }
}
