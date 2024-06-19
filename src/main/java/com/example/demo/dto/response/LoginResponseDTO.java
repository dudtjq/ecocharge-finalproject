package com.example.demo.dto.response;
import com.example.demo.entity.User;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.Column;
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

    private String phoneNumber;

    private String email;

    private String userName;

    private  String profileImage;

    @JsonFormat(pattern = "yyyy년 MM월 dd일")
    private LocalDate joinDate;

//    private Map<String, String> token; // 인증 토큰 (핵심)


    public LoginResponseDTO(User user) {
        this.email = user.getEmail();
        this.userName = user.getUserName();
        this.joinDate = LocalDate.from(user.getJoinDate()); // LocalDateTime 타입이 다르면 from을 이용해서 넣어라!

        this.profileImage=getProfileImage();

        this.phoneNumber = "010-1234-5678";
//        this.token = token;

    }
}