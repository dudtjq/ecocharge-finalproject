package com.example.demo.dto.response;

import com.example.demo.entity.Board;
import lombok.*;

import java.time.format.DateTimeFormatter;

@Getter @Setter @EqualsAndHashCode
@ToString @AllArgsConstructor
@NoArgsConstructor @Builder
public class BoardDetailResponseDTO {
    
    private int count;

    private Long boardNo;

    private String bAddress;

    private String bWriter;

    private String bTitle;

    private String bContent;

    private String bProfileImage;
    
    private String createDate;
    
    private int viewCount;

    public BoardDetailResponseDTO(Board board, int count){
        this.boardNo = board.getBoardNo();
        this.bAddress = board.getBAddress();
        this.bWriter = board.getBWriter();
        this.bTitle = board.getBTitle();
        this.bContent = board.getBContent();
        this.bProfileImage = board.getBProfileImage();
        this.createDate = board.getCreateDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        this.viewCount = board.getViewCount();
        this.count = count;
    }





}
