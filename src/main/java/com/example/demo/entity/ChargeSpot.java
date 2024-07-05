package com.example.demo.entity;

import jakarta.persistence.*;
import lombok.*;

@Getter @Setter @ToString
@AllArgsConstructor @NoArgsConstructor
@EqualsAndHashCode @Builder
@Entity
@Table(name = "tbl_charge_spots")
public class ChargeSpot {

    private String addr; // 충전소 주소

    private String statNm; // 충전소 명

    private String facilityBig; // 시설구분(대)

    private String facilitySmall; // 시설구분(소)

    private String limitYn; // 이용제한

    @Id
    private String statId; // 충전소 ID

    private String latLng; // 위도 경도

}
