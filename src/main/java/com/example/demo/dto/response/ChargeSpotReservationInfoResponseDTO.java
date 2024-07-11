package com.example.demo.dto.response;

import lombok.*;

@Getter @Setter @ToString
@EqualsAndHashCode @AllArgsConstructor @NoArgsConstructor
@Builder
public class ChargeSpotReservationInfoResponseDTO {

    private String addr; // 충전소 주소

    private String statNm; // 충전소 명

    private String facilityBig; // 시설구분(대)

    private String facilitySmall; // 시설구분(소)

    private String limitYn; // 이용제한

    private String statId; // 충전소 ID
}
