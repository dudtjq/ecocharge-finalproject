package com.example.demo.dto.request;

import lombok.*;
import org.springframework.web.multipart.MultipartFile;

@Getter @Setter @ToString
@EqualsAndHashCode @NoArgsConstructor @AllArgsConstructor
@Builder
public class BoardUpdateRequestDTO {

    private Long boardNo;

    private String bAddress;

    private String bWriter;

    private String bTitle;

    private String bContent;

    private String bProfileImage;


}
