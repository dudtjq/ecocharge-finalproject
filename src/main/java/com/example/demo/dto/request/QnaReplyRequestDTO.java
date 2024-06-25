package com.example.demo.dto.request;

import com.example.demo.entity.BoardReply;
import com.example.demo.entity.QnaReply;
import lombok.*;

@Getter @Setter @ToString @EqualsAndHashCode
@AllArgsConstructor @NoArgsConstructor
@Builder
public class QnaReplyRequestDTO {

    private Long qnaNo;

    private String replyWriter;

    private String replyText;


    public QnaReply toEntity(){
        return QnaReply.builder()
                .qnaNo(this.qnaNo)
                .replyWriter(this.replyWriter)
                .replyText(this.replyText)
                .build();

    }


}
