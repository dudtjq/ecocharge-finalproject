package com.example.demo.dto.response;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Getter @Setter @ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class GoogleUserResponseDTO {

    @JsonProperty("given_name")
    private String nickname;

    private String email;

}
