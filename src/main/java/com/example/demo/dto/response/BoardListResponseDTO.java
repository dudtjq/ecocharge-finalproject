package com.example.demo.dto.response;


import com.example.demo.common.PageMaker;
import lombok.*;

import java.util.List;

@Setter @Getter @ToString @EqualsAndHashCode
@NoArgsConstructor @AllArgsConstructor
@Builder
public class BoardListResponseDTO {


    private String error; // 메세지 담을 필드

    private List<BoardDetailResponseDTO> boards; // qna 목록
    
    private PageMaker pageMaker;
    
    private int[] count;


}
