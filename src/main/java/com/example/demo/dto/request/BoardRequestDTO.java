package com.example.demo.dto.request;

import com.example.demo.entity.Board;
import com.example.demo.entity.User;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

@Getter @Setter @ToString
@EqualsAndHashCode @AllArgsConstructor @NoArgsConstructor
@Builder
public class BoardRequestDTO {
    private String bAddress;

    private String bWriter;

    private String bTitle;

    private String bContent;

    private MultipartFile bProfileImage;

    // 엔터티 변환
    // 나중에 User 추가해주기
    public Board toEntity(String s, User user){
        return Board.builder()
                .bAddress(bAddress)
                .bWriter(bWriter)
                .bTitle(bTitle)
                .bContent(bContent)
                .bProfileImage(s)
                .user(user)
                .build();
    }






}
