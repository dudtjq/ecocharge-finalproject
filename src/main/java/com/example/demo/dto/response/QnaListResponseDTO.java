package com.example.demo.dto.response;

import lombok.*;

import java.util.List;

@Setter @Getter @ToString
@EqualsAndHashCode @NoArgsConstructor @AllArgsConstructor
@Builder
public class QnaListResponseDTO {

    private String error; // 메세지 담을 필드

    private List<QnaDetailResponseDTO> qnas; // qna 목록

}


