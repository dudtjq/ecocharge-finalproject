package com.example.demo.dto.response;

import lombok.*;

@Getter @Setter @ToString
@EqualsAndHashCode @AllArgsConstructor
@NoArgsConstructor
@Builder
public class ChargeSpotDetailResponseDTO {


    private String address;

    private String statNm;

    private String facilityBig;

    private String facilitySmall;
    


}
