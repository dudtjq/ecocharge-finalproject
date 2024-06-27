package com.example.demo.dto.response;

import com.example.demo.entity.User;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

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

        private Profile profile;

        @Getter
        @Setter
        @ToString
        public static class Profile {
            private String nickname;

        }
    }

    public User toEntity(String accessToken, String phoneNumber) {
        return User.builder()
                .accessToken(accessToken)
                .userName(this.kakaoAccount.profile.nickname)
                .phoneNumber(phoneNumber)
                .loginMethod(User.LoginMethod.KAKAO)
                .build();
    }

}


