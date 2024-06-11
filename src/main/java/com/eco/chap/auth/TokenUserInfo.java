package com.eco.chap.auth;

import com.eco.chap.userapi.entity.Role;
import lombok.*;

@Getter @ToString
@EqualsAndHashCode
@NoArgsConstructor @AllArgsConstructor
@Builder
public class TokenUserInfo {

    private String userId;
    private String email;
    private Role role;

}
