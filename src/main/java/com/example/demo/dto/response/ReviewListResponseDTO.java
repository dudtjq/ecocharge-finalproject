package com.example.demo.dto.response;

import lombok.*;

import java.util.List;

@Getter @Setter @ToString @EqualsAndHashCode
@NoArgsConstructor @AllArgsConstructor
public class ReviewListResponseDTO {

    private int count; // 리뷰 수

    private List<ReviewDetailResponseDTO> reviewList;

}
