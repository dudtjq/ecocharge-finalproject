package com.example.demo.repository;

import com.example.demo.dto.request.ChargeSpotRequestDTO;
import com.example.demo.entity.ChargeSpot;
import com.example.demo.entity.QChargeSpot;
import com.example.demo.entity.QCharger;
import com.querydsl.core.BooleanBuilder;
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
        BooleanBuilder builder = new BooleanBuilder();

        // 조건 추가
        if (!requestDTO.getLimitYn().isEmpty()) {
            builder.and(chargeSpot.limitYn.like(requestDTO.getLimitYn()));
        }
        if (!requestDTO.getChgerType().isEmpty()) {
            builder.and(charger.chgerType.like("%" + requestDTO.getChgerType() + "%"));
        }
        if (!requestDTO.getPowerType().isEmpty()) {
            builder.and(charger.powerType.like(requestDTO.getPowerType() + "%"));
        }


        return   jpaQueryFactory.select(chargeSpot)
                .from(chargeSpot)
                .leftJoin(chargeSpot.chargerList, charger)
                .where(builder)
                .fetch();
    }
}
