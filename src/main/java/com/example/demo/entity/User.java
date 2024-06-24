package com.example.demo.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Getter @Setter @ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "tbl_user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String userId;

    @Column(nullable = false ,unique = true)
    private String email;

    @Column(nullable = false)
    private String userName;

    @Column(nullable = false)
    private  String password;

    @CreationTimestamp
    private LocalDateTime joinDate;

    @Column(nullable = false)
    private String phoneNumber;

    private String profileImg; // 프로필 이미지 경로

    private String accessToken; // 소셜로그인 시 발급받는 accessToken

    @Column(length = 400)
    private String refreshToken; // 리프레시 토큰의 값

    private Date refreshTokenExpiryDate; // 리프레시 토큰의 만료일

    @Enumerated(value = EnumType.STRING)
    private LoginMethod loginMethod;

    @OneToMany(mappedBy = "user")
    private List<Qna> qnaList;

    @OneToMany(mappedBy = "user")
    private List<Board> boardList;



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
    COMMON, KAKAO, NAVER, GOOGLE
    }
}
