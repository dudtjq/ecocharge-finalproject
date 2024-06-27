package com.example.demo.dto.response;

import com.example.demo.entity.User;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.Map;

@Getter
@Setter
@ToString
public class KakaoUserResponseDTO {


    @JsonProperty("connected_at")
    private LocalDateTime connectedAt;

    @JsonProperty("kakao_account")
    private KakaoAccount kakaoAccount;



    @Setter @Getter @ToString
    public static class KakaoAccount {

        private String email;

        private Profile profile;

        private String phoneNumber;

        @Getter
        @Setter
        @ToString
        public static class Profile {
            private String nickname;


        }
    }

    public User toEntity(String accessToken, String phoneNumber) {
        return User.builder()
                .email(this.kakaoAccount.email)
                .userName(this.kakaoAccount.profile.nickname)
                .phoneNumber(phoneNumber)
                .accessToken(accessToken)
                .loginMethod(User.LoginMethod.KAKAO)
                .build();
    }

}


