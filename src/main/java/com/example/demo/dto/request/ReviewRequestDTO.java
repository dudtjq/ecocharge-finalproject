package com.example.demo.dto.request;


import com.example.demo.entity.ChargeSpot;
import com.example.demo.entity.Review;
import com.example.demo.entity.User;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.time.LocalDateTime;

@Getter @Setter @ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReviewRequestDTO {

    @NotBlank
    private long reviewNo;

    @NotBlank
    private String content;

    @NotBlank
    private String userId;

//    @NotBlank
    private String statId;

    private LocalDateTime reviewDate;

    public Review toEntity(User user, ChargeSpot chargeSpot) {
        return Review.builder()
                .reviewNo(reviewNo)
                .content(content)
                .user(user)
                .chargeSpot(chargeSpot)
                .reviewDate(reviewDate)
                .build();
    }

}
