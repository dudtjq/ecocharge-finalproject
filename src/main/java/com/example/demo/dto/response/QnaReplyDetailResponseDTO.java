package com.example.demo.dto.response;

import com.example.demo.entity.BoardReply;
import com.example.demo.entity.QnaReply;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Getter @Setter @ToString
@EqualsAndHashCode @AllArgsConstructor
@NoArgsConstructor
public class QnaReplyDetailResponseDTO {

    private Long replyNo;

    private String replyWriter;

    private String replyText;

    @CreationTimestamp
    @JsonFormat(pattern = "yyyy년 MM월 dd일 HH:mm")
    private LocalDateTime replyDate;

    @CreationTimestamp
    @JsonFormat(pattern = "yyyy년 MM월 dd일 HH:mm (수정됨)")
    private LocalDateTime updateDate;

    public QnaReplyDetailResponseDTO(QnaReply qnaReply){

        this.replyNo = qnaReply.getReplyNo();
        this.replyWriter = qnaReply.getReplyWriter();
        this.replyText = qnaReply.getReplyText();
        this.replyDate = qnaReply.getReplyDate();

    }



}
