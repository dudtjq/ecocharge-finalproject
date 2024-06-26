package com.example.demo.dto.request;

import com.example.demo.entity.Board;
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
    public Board toEntity(){
        String profileImage;
        if (this.bProfileImage != null) {
            profileImage = this.bProfileImage.toString();
        } else {
            profileImage = "";
        }
        return Board.builder()
                .bAddress(bAddress)
                .bWriter(bWriter)
                .bTitle(bTitle)
                .bContent(bContent)
                .bProfileImage(profileImage)
                .build();
    }






}
