package com.example.demo.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Getter @Setter @ToString
@EqualsAndHashCode @AllArgsConstructor
@NoArgsConstructor
public class QnaReplyUpdateRequestDTO {

    private Long replyNo;

    private String replyText;

    @CreationTimestamp
    @JsonFormat(pattern = "yyyy년 MM월 dd일 HH:mm (수정됨)")
    private LocalDateTime updateDate;

}
