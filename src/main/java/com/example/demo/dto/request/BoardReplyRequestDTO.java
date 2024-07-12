package com.example.demo.dto.request;

import com.example.demo.entity.Board;
import com.example.demo.entity.BoardReply;
import lombok.*;

@Getter @Setter @ToString
@EqualsAndHashCode @NoArgsConstructor
@AllArgsConstructor @Builder
public class BoardReplyRequestDTO {

    private Long boardNo;

    private String replyWriter;

    private String replyText;

    private Long replyNo;

    private String userId;

    public BoardReply toEntity(Board board){
        return BoardReply.builder()
                .board(board)
                .replyNo(this.replyNo)
                .replyWriter(this.replyWriter)
                .replyText(this.replyText)
                .userId(this.userId)
                .build();
    }

}
