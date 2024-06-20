package com.example.demo.dto.request;

import jakarta.persistence.Id;
import lombok.*;

@Getter @Setter @ToString
@EqualsAndHashCode @NoArgsConstructor @AllArgsConstructor
@Builder
public class QnaUpdateRequestDTO {

    @Id
    private Long qnaNo;

    private String qTitle;

    private String qContent;

    private String qAnswer;

}
