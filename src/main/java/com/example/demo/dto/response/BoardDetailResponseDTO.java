package com.example.demo.dto.response;

import com.example.demo.entity.Board;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

@Getter @Setter @EqualsAndHashCode
@ToString @AllArgsConstructor
@NoArgsConstructor @Builder
public class BoardDetailResponseDTO {

    private Long boardNo;

    private String bAddress;

    private String bWriter;

    private String bTitle;

    private String bContent;

    private String bProfileImage;

    public BoardDetailResponseDTO(Board board){
        this.boardNo = board.getBoardNo();
        this.bAddress = board.getBAddress();
        this.bWriter = board.getBWriter();
        this.bTitle = board.getBTitle();
        this.bContent = board.getBContent();
        this.bProfileImage = board.getBProfileImage();
    }





}
