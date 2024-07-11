package com.example.demo.repository;

import com.example.demo.dto.request.ChargeSpotMakerRequestDTO;
import com.example.demo.dto.request.ChargeSpotRequestDTO;
import com.example.demo.dto.response.ChargeSpotMarkerDetailResponseDTO;
import com.example.demo.entity.ChargeSpot;

import java.util.List;

public interface ChargeSpotRepositoryCustom {

    List<ChargeSpot> findAll(ChargeSpotRequestDTO requestDTO);

    List<ChargeSpotMarkerDetailResponseDTO> findInfoList(ChargeSpotMakerRequestDTO requestDTO);
}
