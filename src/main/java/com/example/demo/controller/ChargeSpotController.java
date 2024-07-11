package com.example.demo.controller;

import com.example.demo.dto.request.ChargeSpotInfoRequestDTO;
import com.example.demo.dto.request.ChargeSpotMakerRequestDTO;
import com.example.demo.dto.request.ChargeSpotRequestDTO;
import com.example.demo.dto.response.*;
import com.example.demo.entity.ChargeSpot;
import com.example.demo.service.ChargeSpotService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/chargespot")
public class ChargeSpotController {

    private final ChargeSpotService chargeSpotService;

    // 마커 클릭 시 상세보기 정보
    @PostMapping("/detail")
    public ResponseEntity<?> infoDetail(@RequestBody ChargeSpotMakerRequestDTO requestDTO
            ) {
        
        log.info("/chargespot/marker? GET!! requestDTO: {}", requestDTO);


        List<ChargeSpotMarkerDetailResponseDTO> chargeSpots = chargeSpotService.infoDetail(requestDTO);

        log.info(chargeSpots.toString());

        return ResponseEntity.ok().body(chargeSpots);
    }

    @PostMapping
    private ResponseEntity<?> searchSpot(@RequestBody ChargeSpotRequestDTO requestDTO){
        log.info("/chargespot requestDTO POST! : {}",requestDTO);

        List<ChargeSpotMarkerResponsDTO> spotList = chargeSpotService.findSearch(requestDTO);

        return ResponseEntity.ok().body(spotList);

    }

    @PostMapping("/reservationInfo")
    private ResponseEntity<?> reservationDetail(@RequestBody ChargeSpotInfoRequestDTO requestDTO){

        final List<ChargeSpotReservationInfoResponseDTO> responseDTOS = chargeSpotService.reservationDetail(requestDTO);

        log.info(responseDTOS.toString());

        return ResponseEntity.ok().body(responseDTOS);

    }
    









}
