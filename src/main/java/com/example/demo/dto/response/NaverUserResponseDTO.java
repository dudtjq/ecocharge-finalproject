package com.example.demo.dto.response;
import com.example.demo.entity.User;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Column;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
public class NaverUserResponseDTO {

    private long id;

    @JsonProperty("connected_at")
    private LocalDateTime connectedAt;

    @JsonProperty("naver_account")
    private NaverAccount naverAccount;

    @Setter @Getter @ToString
    public static class NaverAccount {

        private String email;
        private Profile profile;

        @Column(unique = true)
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

    public User toEntity(String accessToken) {
        return User.builder()
                .email(this.naverAccount.email)
                .userName(this.naverAccount.profile.nickname)
                .profileImg(this.naverAccount.profile.profileImageUrl)
                .build();
    }

}


