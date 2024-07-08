package com.example.demo.dto.request;

import com.example.demo.entity.Qna;
import com.example.demo.entity.User;
import lombok.*;



@Getter @Setter @ToString
@NoArgsConstructor @AllArgsConstructor
@EqualsAndHashCode
@Builder
public class QnaRequestDTO {

    private String qCategory;

    private String qTitle;

    private String qContent;


    // 엔터티 변환
    public Qna toEntity(User user){

        return Qna.builder()
                .qTitle(qTitle)
                .qContent(qContent)
                .qCategory(qCategory)
                .user(user)
                .build();

    }
}
