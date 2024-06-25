package com.example.demo.dto.response;

import lombok.*;

import java.util.List;

@Getter @Setter @ToString
@EqualsAndHashCode @AllArgsConstructor
@NoArgsConstructor @Builder
public class BoardReplyListResponseDTO {

    private int count; // 댓글 수

    private List<BoardReplyDetailResponseDTO> replies; // 실제 댓글 리스트
}
