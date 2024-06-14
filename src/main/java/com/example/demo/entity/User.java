package com.example.demo.entity;

import com.example.demo.qnaapi.Qna;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Setter @Getter @ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "tbl_user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "user_id")
    private String id;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String userName;

    @CreationTimestamp
    private LocalDateTime joinDate;

    @Column(unique = true, nullable = false)
    private String phoneNumber;

    private String profileImg; // 프로필 이미지 경로

    private String kakaoAccessToken; // 카카오 로그인 시 발급받는 accessToken
    private String googleAccessToken; // 구글 로그인 시 발급받는 accessToken
    private String naverAccessToken; // 네이버 로그인 시 발급받는 accessToken

    @Column(length = 400)
    private String refreshToken; // 리프레시 토큰의 값

    private Date refreshTokenExpiryDate; // 리프레시 토큰의 만료일

    private LoginMethod loginMethod;


    // 카카오 access token 저장하는 메서드
    public void changeKakaoAccessToken(String accessToken) {
        this.kakaoAccessToken = accessToken;
    }

    // 구글 access token 저장하는 메서드
    public void changeGoogleAccessToken(String accessToken) {
        this.googleAccessToken = accessToken;
    }

    // 네이버 access token 저장하는 메서드
    public void changeNaverAccessToken(String accessToken) {
        this.naverAccessToken = accessToken;
    }

    public void changeRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    public void changeRefreshExpiryDate(Date date) {
        this.refreshTokenExpiryDate = date;
    }


    public enum LoginMethod{
        COMMON, KAKAO, NAVER, GOOGLE
    }


}
