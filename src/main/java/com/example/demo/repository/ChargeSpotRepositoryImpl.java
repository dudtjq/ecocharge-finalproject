package com.example.demo.repository;

import com.example.demo.dto.request.ChargeSpotRequestDTO;
import com.example.demo.entity.ChargeSpot;
import com.example.demo.entity.QChargeSpot;
import com.example.demo.entity.QCharger;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.util.List;

import static com.example.demo.entity.QChargeSpot.chargeSpot;
import static com.example.demo.entity.QCharger.charger;

@RequiredArgsConstructor
public class ChargeSpotRepositoryImpl implements ChargeSpotRepositoryCustom{

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public List<ChargeSpot> findAll(ChargeSpotRequestDTO requestDTO) {
       return   jpaQueryFactory.select(chargeSpot)
                .from(chargeSpot)
                .leftJoin(chargeSpot.chargerList, charger)
                .where(chargeSpot.limitYn.eq(requestDTO.getLimitYn()),
                        charger.chgerType.eq(requestDTO.getChgerType()),
                        charger.powerType.eq(requestDTO.getPowerType())
                )
                .fetch();

    }
}
