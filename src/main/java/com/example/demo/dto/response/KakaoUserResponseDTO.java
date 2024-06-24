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

        private String email;

        private Profile profile;

        private String phoneNumber;

        @Getter
        @Setter
        @ToString
        public static class Profile {
            private String nickname;

            @JsonProperty("profile_image_url")
            private String profileImageUrl;
        }
    }

    public User toEntity(String accessToken, String phoneNumber) {
        return User.builder()
                .email(this.kakaoAccount.email)
                .userName(this.kakaoAccount.profile.nickname)
                .profileImg(this.kakaoAccount.profile.profileImageUrl)
                .loginMethod(User.LoginMethod.KAKAO)
                .build();
    }

}


