package com.example.demo.dto.response;

import com.example.demo.entity.User;
import lombok.*;

@Setter @Getter @ToString
@EqualsAndHashCode(of = "email")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserSignUpResponseDTO {

    private String email;

    private String userName;

    public UserSignUpResponseDTO(User saved) {
        this.email = saved.getEmail();
        this.userName = saved.getUserName();
    }


}
