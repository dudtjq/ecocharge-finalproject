package com.example.demo.dto.response;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Getter
@Setter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class NaverResponseDTO {
    @JsonProperty("response")
    private Response response;


    @Getter @Setter @ToString
    @EqualsAndHashCode
    public static class Response {
        private String email;
        private String nickname;

        @JsonProperty("profile_image")
        private String profileImage;
    }
}