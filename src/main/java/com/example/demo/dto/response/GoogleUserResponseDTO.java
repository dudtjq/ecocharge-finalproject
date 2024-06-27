package com.example.demo.dto.response;

import com.example.demo.entity.User;
import lombok.*;




@Getter @Setter @ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class GoogleUserResponseDTO {

    private String name;

    private String phoneNumber;

public User toEntity(String accessToken,String phoneNumber) {
    return User.builder()
            .userName(this.name)
            .accessToken(accessToken)
            .phoneNumber(phoneNumber)
            .loginMethod(User.LoginMethod.GOOGLE)
            .build();
}


}
