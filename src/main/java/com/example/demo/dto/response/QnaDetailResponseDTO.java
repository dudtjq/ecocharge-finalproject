package com.example.demo.dto.response;

import com.example.demo.entity.Qna;
import lombok.*;

import java.time.format.DateTimeFormatter;

@Getter @Setter @ToString
@EqualsAndHashCode @AllArgsConstructor
@NoArgsConstructor
@Builder
public class QnaDetailResponseDTO {

    private Long qnaNo;

    private String qCategory;

    private String qTitle;

    private String qContent;
    
    private String date;
    
    private String qwriter;
    
    private int count;



    // 엔터티를 DTO로 변경하는 생성자
    public QnaDetailResponseDTO(Qna qna, int count) {
        this.qnaNo = qna.getQnaNo();
        this.qTitle = qna.getQTitle();
        this.qContent = qna.getQContent();
        this.qCategory = qna.getQCategory();
        this.date = qna.getCreateDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        this.qwriter = qna.getUser().getUserName();
        this.count = count;
    }
}
