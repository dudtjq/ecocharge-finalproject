package com.example.demo.qnaapi.dto.request;

import com.example.demo.entity.User;
import com.example.demo.qnaapi.entity.Qna;
import lombok.*;

@Getter @Setter @ToString
@NoArgsConstructor @AllArgsConstructor
@EqualsAndHashCode
@Builder
public class QnaRequestDTO {

    private String qTitle;

    private String qContent;


    // 엔터티 변환
    public Qna toEntity(User user){

        return Qna.builder()
                .qTitle(qTitle)
                .qContent(qContent)
                .user(user)
                .build();

    }
}
