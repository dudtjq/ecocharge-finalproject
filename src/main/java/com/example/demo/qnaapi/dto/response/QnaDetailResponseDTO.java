package com.example.demo.qnaapi.dto.response;

import com.example.demo.qnaapi.entity.Qna;
import lombok.*;

@Getter @Setter @ToString
@EqualsAndHashCode @AllArgsConstructor
@NoArgsConstructor
@Builder
public class QnaDetailResponseDTO {

    private Long qnaNo;

    private String qTitle;

    private String qContent;

    private String qAnswer;

    // 엔터티를 DTO로 변경하는 생성자


    public QnaDetailResponseDTO(Qna qna) {
        this.qnaNo = qna.getQnaNo();
        this.qTitle = qna.getQTitle();
        this.qContent = qna.getQContent();
        this.qAnswer = qna.getQAnswer();
    }
}
