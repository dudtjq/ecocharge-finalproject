package com.example.demo.dto.response;

import com.example.demo.entity.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter @ToString
@NoArgsConstructor
@AllArgsConstructor
public class ModifyUserResponseDTO {

    private String userName;
    private String phoneNumber;
    private String password;

    public ModifyUserResponseDTO(User user) {
        this.userName = user.getUserName();
        this.phoneNumber = user.getPhoneNumber();
        if (user.getPhoneNumber().contains("ECO")) {
            String siteUserPhoneNumber = user.getPhoneNumber().substring(3);
            this.phoneNumber = siteUserPhoneNumber;
        }
        this.password = user.getPassword();
    }

}
