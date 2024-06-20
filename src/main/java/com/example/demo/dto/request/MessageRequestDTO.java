package com.example.demo.dto.request;

import com.example.demo.entity.User;
import lombok.*;

@Getter @Setter
@ToString @EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor

public class MessageRequestDTO {
    private String phoneNumber;
    private String verifivationCodeInput;

    public User toEntity(String request) {
        return User.builder()
                .phoneNumber(this.phoneNumber)
                .build();
    }

}
