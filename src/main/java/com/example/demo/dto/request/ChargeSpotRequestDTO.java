package com.example.demo.dto.request;

import com.example.demo.entity.ChargeSpot;
import lombok.*;

@Getter @ToString @Setter
@EqualsAndHashCode @AllArgsConstructor
@NoArgsConstructor
@Builder
public class ChargeSpotRequestDTO {

    private String facilityBig; // 시설구분(대)

    private String facilitySmall; // 시설구분(소)

    private String limitYn; // 이용제한

    private String statId; // 충전소 ID

    private String latLng; // 위도 경도

    public ChargeSpot toEntity(){

        return ChargeSpot.builder()
                .facilityBig(facilityBig)
                .facilitySmall(facilitySmall)
                .limitYn(limitYn)
                .statId(statId)
                .latLng(latLng)
                .build();

    }
}
