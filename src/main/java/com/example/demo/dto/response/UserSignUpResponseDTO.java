package com.example.demo.dto.response;

import com.example.demo.entity.User;
import lombok.*;

@Setter @Getter @ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserSignUpResponseDTO {

    private String id;
    private String userName;

    public UserSignUpResponseDTO(User saved) {
        this.id = saved.getIdentify();
        this.userName = saved.getUserName();
    }

}