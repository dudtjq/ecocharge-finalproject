package com.example.demo.dto.response;
import com.example.demo.entity.User;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Column;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.Map;

@Getter
@Setter
@ToString
public class NaverUserResponseDTO {

    @JsonProperty("response")
    private NaverUserDetail naverUserDetail;

    @Setter @Getter @ToString
    public static class NaverUserDetail {

        private String name;
        private String email;
        @JsonProperty("mobile")
        private String phoneNumber;

    }

    public User toEntity(String accessToken,String phoneNumber) {
        return User.builder()
                .email(this.naverUserDetail.email)
                .userName(this.naverUserDetail.name)
                .phoneNumber(phoneNumber)
                .accessToken(accessToken)
                .loginMethod(User.LoginMethod.NAVER)
                .build();
    }
}


