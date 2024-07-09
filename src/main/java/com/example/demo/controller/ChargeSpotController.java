package com.example.demo.controller;

import com.example.demo.dto.request.ChargeSpotRequestDTO;
import com.example.demo.dto.response.ChargeSpotMarkerResponsDTO;
import com.example.demo.dto.response.ChargerSpotResponseDTO;
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
    
    @GetMapping("/marker")
    public ResponseEntity<?> createChargeSpotMarker(@RequestParam(name = "lat") double lat, @RequestParam(name = "lng") double lng) {
        
        log.info("/chargespot/marker?lat={}&lng={} GET!!", lat, lng);
        
        List<ChargeSpotMarkerResponsDTO> markerList = chargeSpotService.getMarker(lat, lng);
        
        return ResponseEntity.ok().body(markerList);
    }

    @PostMapping
    private ResponseEntity<?> searchSpot(@RequestBody ChargeSpotRequestDTO requestDTO,
                                         @RequestParam(name = "lat") double lat, @RequestParam(name = "lng") double lng){

        log.info("/chargespot requestDTO POST! : {}",requestDTO);

        List<ChargeSpot> spotList = chargeSpotService.findSearch(requestDTO);

        List<ChargeSpotMarkerResponsDTO> markerList = chargeSpotService.getMarker(lat, lng);

        log.info(String.valueOf(spotList));

        return ResponseEntity.ok().body(spotList);

    }





}
