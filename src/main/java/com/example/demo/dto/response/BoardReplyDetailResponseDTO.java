package com.example.demo.dto.response;

import com.example.demo.entity.BoardReply;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.Column;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Getter @Setter @ToString
@EqualsAndHashCode @AllArgsConstructor @NoArgsConstructor
@Builder
public class BoardReplyDetailResponseDTO {

    private Long replyNo;

    private String replyWriter;

    private String replyText;

    @CreationTimestamp
    @JsonFormat(pattern = "yyyy년 MM월 dd일 HH:mm")
    private LocalDateTime replyDate;

    @CreationTimestamp
    @JsonFormat(pattern = "yyyy년 MM월 dd일 HH:mm (수정됨)")
    private LocalDateTime updateDate;

    private String userId;

    public BoardReplyDetailResponseDTO(BoardReply boardReply){

        this.replyNo = boardReply.getReplyNo();
        this.replyWriter = boardReply.getReplyWriter();
        this.replyText = boardReply.getReplyText();
        this.replyDate = boardReply.getReplyDate();
        this.updateDate = boardReply.getUpdateReplyDate();
        this.userId = boardReply.getUserId();
    }



}
