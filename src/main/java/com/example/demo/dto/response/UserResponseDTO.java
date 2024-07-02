package com.example.demo.dto.response;

import com.example.demo.entity.User;
import lombok.*;

@Getter @Setter @ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserResponseDTO {

    private String userName;
    private String phoneNumber;
    private User.LoginMethod loginMethod;

    public UserResponseDTO (User user) {
        this.userName = user.getUserName();
        this.phoneNumber = user.getPhoneNumber();
        if (user.getPhoneNumber().contains("ECO")) {
            String siteUserPhoneNumber = user.getPhoneNumber().substring(3);
            this.phoneNumber = siteUserPhoneNumber;
        }
        this.loginMethod = user.getLoginMethod();
    }

}
