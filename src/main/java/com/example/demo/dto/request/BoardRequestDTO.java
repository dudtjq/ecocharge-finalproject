package com.example.demo.dto.request;

import com.example.demo.entity.Board;
import lombok.*;

@Getter @Setter @ToString
@EqualsAndHashCode @AllArgsConstructor @NoArgsConstructor
@Builder
public class BoardRequestDTO {
    private String bAddress;

    private String bWriter;

    private String bTitle;

    private String bContent;


    private String bProfileImage;

    // 엔터티 변환
    // 나중에 User 추가해주기
    public Board toEntity(){

        return Board.builder()
                .bAddress(bAddress)
                .bWriter(bWriter)
                .bTitle(bTitle)
                .bContent(bContent)
                .bProfileImage(bProfileImage)
                .build();

    }






}
