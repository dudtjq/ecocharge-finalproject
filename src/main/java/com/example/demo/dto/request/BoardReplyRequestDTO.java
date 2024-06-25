package com.example.demo.dto.request;

import com.example.demo.entity.BoardReply;
import lombok.*;

@Getter @Setter @ToString
@EqualsAndHashCode @NoArgsConstructor
@AllArgsConstructor @Builder
public class BoardReplyRequestDTO {

    private Long boardNo;

    private String replyWriter;

    private String replyText;

    public BoardReply toEntity(){
        return BoardReply.builder()
                .boardNo(this.boardNo)
                .replyWriter(this.replyWriter)
                .replyText(this.replyText)
                .build();
    }

}
