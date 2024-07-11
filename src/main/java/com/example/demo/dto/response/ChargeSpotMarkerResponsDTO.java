package com.example.demo.dto.response;

import lombok.*;

@Getter @Setter @ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ChargeSpotMarkerResponsDTO {
    
    private String lat; // 위도
    
    private String lng; // 경도
    
}
