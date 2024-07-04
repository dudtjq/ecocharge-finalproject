package com.example.demo.dto.request;

import com.example.demo.entity.User;
import lombok.*;

@Getter @Setter @ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReviewModifyRequestDTO {

    private Long reviewNo;
    private String content;
    private User user;

}
