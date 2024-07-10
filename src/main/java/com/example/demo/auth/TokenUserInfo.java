package com.example.demo.auth;

import com.example.demo.entity.Role;
import lombok.*;

@Getter @ToString
@EqualsAndHashCode
@NoArgsConstructor @AllArgsConstructor
@Builder
public class TokenUserInfo {

    private String userId;

}
