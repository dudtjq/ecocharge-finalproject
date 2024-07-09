package com.example.demo.dto.response;

import com.example.demo.entity.ChargeSpot;
import com.example.demo.entity.Charger;
import lombok.*;

@Getter @Setter @ToString
@EqualsAndHashCode @AllArgsConstructor
@NoArgsConstructor @Builder
public class ChargerSpotResponseDTO {

    private String bnm;
    private String busiNm;
    private String chargerId;
    private String chgerType;
    private String powerType;
    private String statId;
    private String addr;
    private String facilityBig;
    private String facilitySmall;
    private String latLng;
    private String limitYn;
    private String statNm;





}
