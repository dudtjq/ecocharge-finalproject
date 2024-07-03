package com.example.demo.dto.request;

import lombok.*;

@Getter @Setter @ToString
@EqualsAndHashCode @NoArgsConstructor @AllArgsConstructor
@Builder
public class QnaUpdateRequestDTO {


    private Long qnaNo;

    private String qCategory;

    private String qTitle;

    private String qContent;


}
