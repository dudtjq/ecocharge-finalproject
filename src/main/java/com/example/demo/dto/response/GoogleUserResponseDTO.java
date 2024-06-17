package com.example.demo.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;

import java.time.LocalDateTime;


@Getter @Setter @ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class GoogleUserResponseDTO {

    private String email;

    @JsonProperty("profile_image")
    private String profileImage;

    private String phoneNumber;

}
