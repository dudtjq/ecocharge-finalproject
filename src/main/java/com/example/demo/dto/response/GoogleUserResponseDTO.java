package com.example.demo.dto.response;

import com.example.demo.entity.User;
import lombok.*;




@Getter @Setter @ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class GoogleUserResponseDTO {


    private String email;
    private String name;
    private String picture;
    private String phoneNumber;

public User toEntity(String accessToken,String phoneNumber) {
    return User.builder()
            .email(this.email)
            .userName(this.name)
            .phoneNumber(phoneNumber)
            .profileImg(this.picture)
            .accessToken(accessToken)
            .loginMethod(User.LoginMethod.GOOGLE)
            .build();
}

}
