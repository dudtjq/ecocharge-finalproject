package com.example.demo.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.Date;

@Getter @ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "tbl_user")
public class User {

    @Id
    @Column(name = "user_id")
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String userName;

    @CreationTimestamp
    private LocalDateTime joinDate;

    @Column(unique = true)
    private String phoneNumber;

    private String profileImg; // 프로필 이미지 경로

    private String accessToken; // 소셜로그인 시 발급받는 accessToken

    @Column(length = 400)
    private String refreshToken; // 리프레시 토큰의 값

    private Date refreshTokenExpiryDate; // 리프레시 토큰의 만료일

    private LoginMethod loginMethod;

    // 소셜 로그인 access token 저장하는 메서드
    public void changeAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public void changeRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    public void changeRefreshExpiryDate(Date date) {
        this.refreshTokenExpiryDate = date;
    }


    public enum LoginMethod{
        KAKAO, NAVER, GOOGLE
    }


}
