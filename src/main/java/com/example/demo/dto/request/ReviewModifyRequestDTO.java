package com.example.demo.dto.request;

import com.example.demo.entity.User;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Getter @Setter @ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReviewModifyRequestDTO {

    private Long reviewNo;
    private String content;
    private String userId;

    @CreationTimestamp
    private LocalDateTime updateReviewDate;

}
