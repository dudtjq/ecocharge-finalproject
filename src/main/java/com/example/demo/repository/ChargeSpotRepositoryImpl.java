package com.example.demo.repository;

import com.example.demo.dto.request.ChargeSpotInfoRequestDTO;
import com.example.demo.dto.request.ChargeSpotMakerRequestDTO;
import com.example.demo.dto.request.ChargeSpotRequestDTO;
import com.example.demo.dto.response.ChargeSpotMarkerDetailResponseDTO;
import com.example.demo.dto.response.ChargeSpotReservationInfoResponseDTO;
import com.example.demo.entity.ChargeSpot;
import com.example.demo.entity.QChargeSpot;
import com.example.demo.entity.QCharger;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.List;

import static com.example.demo.entity.QChargeSpot.chargeSpot;
import static com.example.demo.entity.QCharger.charger;

@RequiredArgsConstructor
public class ChargeSpotRepositoryImpl implements ChargeSpotRepositoryCustom {


    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public List<ChargeSpotMarkerDetailResponseDTO> findInfoList(ChargeSpotMakerRequestDTO requestDTO) {

        String latLng = requestDTO.getLat() + "," + requestDTO.getLng();

        final List<ChargeSpotMarkerDetailResponseDTO> dtoList = jpaQueryFactory.select(Projections.bean(ChargeSpotMarkerDetailResponseDTO.class,
                        chargeSpot.addr,
                        chargeSpot.statNm,
                        chargeSpot.facilityBig,
                        chargeSpot.facilitySmall,
                        chargeSpot.limitYn,
                        chargeSpot.statId))
                .from(chargeSpot)
                .where(chargeSpot.latLng.eq(latLng))
                .fetch();

        return dtoList;
    }

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

    public ChargeSpot reservationInfo(String statId) {


         ChargeSpot fetch = jpaQueryFactory.selectFrom(chargeSpot)
                .where(chargeSpot.statId.eq(statId))
                .fetchOne();

         return fetch;

    }
}
