package com.example.demo.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Getter @Setter @ToString
@AllArgsConstructor @NoArgsConstructor
@EqualsAndHashCode @Builder
@Entity
@Table(name = "tbl_charge_spots")
public class ChargeSpot {

    @Column(name = "addr")
    private String addr; // 충전소 주소

    @Column(name = "stat_nm")
    private String statNm; // 충전소 명

    @Column(name = "facility_big")
    private String facilityBig; // 시설구분(대)

    @Column(name = "facility_small")
    private String facilitySmall; // 시설구분(소)

    @Column(name = "limit_yn")
    private String limitYn; // 이용제한

    @Id
    @Column(name = "stat_id")
    private String statId; // 충전소 ID

    @Column(name = "lat_lng")
    private String latLng; // 위도 경도
    
    @OneToMany(mappedBy = "chargeSpot")
    private List<Charger> chargerList;
    
    @OneToMany(mappedBy = "chargeSpot")
    private List<Reservation> reservationList;

}
