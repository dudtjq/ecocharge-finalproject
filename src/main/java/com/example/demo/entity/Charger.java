package com.example.demo.entity;

import jakarta.persistence.*;
import lombok.*;

@Getter @Setter @ToString
@AllArgsConstructor @NoArgsConstructor
@EqualsAndHashCode
@Builder
@Entity
@Table(name = "tbl_charger")
public class Charger {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(name = "bnm")
    private String bnm; //  운영기관(대)

    @Column(name = "busi_nm")
    private String busiNm; //  운영기관(소)

    @Column(name = "power_type")
    private String powerType; // 급속 충전량

    @Column(name = "chger_type")
    private String chgerType; // 충전기 타입

    @Column(name = "charger_id")
    private String chargerId; // 충전기 ID

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "stat_id")
    private ChargeSpot chargeSpot;

}
