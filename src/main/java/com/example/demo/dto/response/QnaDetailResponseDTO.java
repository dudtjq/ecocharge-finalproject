package com.example.demo.dto.response;

import com.example.demo.entity.Qna;
import lombok.*;
import lombok.extern.slf4j.Slf4j;

import java.time.format.DateTimeFormatter;

@Getter @Setter @ToString
@EqualsAndHashCode @AllArgsConstructor
@NoArgsConstructor
@Builder
@Slf4j
public class QnaDetailResponseDTO {

    private Long qnaNo;

    private String qCategory;

    private String qTitle;

    private String qContent;

    private String date;

    private String qwriter;

    private int count;

    private String qUserId;

    private String qAnswer;

    private  String qRole;




    // 엔터티를 DTO로 변경하는 생성자
    public QnaDetailResponseDTO(Qna qna, int count) {

        log.info("user: {}", qna.getUser());

        this.qnaNo = qna.getQnaNo();
        this.qTitle = qna.getQTitle();
        this.qContent = qna.getQContent();
        this.qAnswer=qna.getQAnswer();
        this.qCategory=qna.getQCategory();
        this.date = qna.getCreateDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        this.qwriter=qna.getUser().getUserName();
        this.qUserId = qna.getUser().getUserId();
        this.qRole= String.valueOf(qna.getUser().getRole());
        this.count = count;
    }
}