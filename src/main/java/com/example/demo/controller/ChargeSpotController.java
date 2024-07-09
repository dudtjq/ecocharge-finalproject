package com.example.demo.controller;

import com.example.demo.dto.response.ChargeSpotMarkerResponsDTO;
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
    public ResponseEntity<?> createChargeSpotMarker(
            @RequestParam(name = "lat") double lat,
            @RequestParam(name = "lng") double lng,
            @RequestParam(name = "zoom") int zoom
    ) {
        
        log.info("/chargespot/marker?lat={}&lng={}&zoom={} GET!!", lat, lng, zoom);
        
        List<ChargeSpotMarkerResponsDTO> markerList = chargeSpotService.getMarker(lat, lng, zoom);
        
        return ResponseEntity.ok().body(markerList);
    }





}
