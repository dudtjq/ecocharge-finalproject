package com.example.demo.dto.request;

import com.example.demo.entity.User;
import lombok.*;

@Setter @Getter @ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ModifyUserRequestDTO {

    private String userName;
    private String phoneNumber;
    private String password;
    private User.LoginMethod loginMethod;
    private String originalPhone;

    public User toEntity() {
        return User.builder()
                .userName(userName)
                .phoneNumber(phoneNumber)
                .password(password)
                .build();
    }

}
