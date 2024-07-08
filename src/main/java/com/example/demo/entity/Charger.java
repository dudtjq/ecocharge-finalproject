package com.example.demo.entity;

import jakarta.persistence.*;
import lombok.*;

@Getter @ToString
@AllArgsConstructor @NoArgsConstructor
@EqualsAndHashCode
@Builder
@Entity
@Table(name = "tbl_charger")
public class Charger {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    
    private String bnm; //  운영기관(대)

    private String busiNm; //  운영기관(소)

    private String powerType; // 급속 충전량

    private String chgerType; // 충전기 타입

    private String chargerId; // 충전기 ID

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "stat_id")
    private ChargeSpot chargeSpot;

}
