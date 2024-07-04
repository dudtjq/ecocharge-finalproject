package com.example.demo.dto.response;

import com.example.demo.entity.Review;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Setter @Getter @ToString
@EqualsAndHashCode @NoArgsConstructor @AllArgsConstructor
@Builder
public class ReviewDetailResponseDTO {

    private Long reviewNo;

    private String content;

    @CreationTimestamp
    @JsonFormat(pattern = "yyyy년 MM월 dd일 HH:mm")
    private LocalDateTime reviewDate;

    @CreationTimestamp
    @JsonFormat(pattern = "yyyy년 MM월 dd일 HH:mm (수정됨)")
    private LocalDateTime updateReviewDate;

    private String userName;

    private String statNm;


    public ReviewDetailResponseDTO(Review review) {
        this.reviewNo = review.getReviewNo();
        this.content = review.getContent();
        this.reviewDate = review.getReviewDate();
        this.updateReviewDate = review.getUpdateReviewDate();
        this.userName = review.getUser().getUserName();
        this.statNm = review.getChargeInfo().getStatNm();
    }
}
