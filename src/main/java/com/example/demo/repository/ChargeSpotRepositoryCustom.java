package com.example.demo.repository;

import com.example.demo.dto.request.ChargeSpotRequestDTO;
import com.example.demo.entity.ChargeSpot;

import java.util.List;

public interface ChargeSpotRepositoryCustom {

    List<ChargeSpot> findAll(ChargeSpotRequestDTO requestDTO);
}
