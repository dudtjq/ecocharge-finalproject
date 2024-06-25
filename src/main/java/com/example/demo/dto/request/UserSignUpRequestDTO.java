package com.example.demo.dto.request;

import com.example.demo.entity.User;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@Setter @Getter @ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserSignUpRequestDTO {

    @NotBlank
    @Size(min = 8, max = 20)
    private String password;

    @NotBlank
    private String phoneNumber;

    @NotBlank
    @Size(min = 2, max = 5)
    private String userName;

    private String id;

    private User.LoginMethod loginMethod;

    public User toEntity( String phone) {
        return User.builder()
                .identify(id)
                .password(password)
                .userName(userName)
                .phoneNumber(phone)
                .loginMethod(User.LoginMethod.COMMON)
                .build();
    }


}
