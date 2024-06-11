package com.eco.chap.dto.response;

import com.eco.chap.userapi.entity.User;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Column;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
public class KakaoUserResponseDTO {

    private long id;

    @JsonProperty("connected_at")
    private LocalDateTime connectedAt;

    @JsonProperty("kakao_account")
    private KakaoAccount kakaoAccount;

    @Column(unique = true)
    private String phoneNumber;




    @Setter @Getter @ToString
    public static class KakaoAccount {

        private String email;
        private Profile profile;


        @Getter
        @Setter
        @ToString
        public static class Profile {
            private String nickname;

            @JsonProperty("profile_image_url")
            private String profileImageUrl;

        }
    }

    public User toEntity(String accessToken) {
        return User.builder()
                .email(this.kakaoAccount.email)
                .userName(this.kakaoAccount.profile.nickname)
                .profileImg(this.kakaoAccount.profile.profileImageUrl)
                .build();
    }

}


