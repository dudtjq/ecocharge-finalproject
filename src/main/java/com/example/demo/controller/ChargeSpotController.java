package com.example.demo.controller;

import com.example.demo.service.ChargerService;
import com.example.demo.service.ChargerSpotService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequestMapping("/charger")
@RequiredArgsConstructor
public class ChargeSpotController {

    private final ChargerSpotService chargerSpotService;


//    @PostMapping
//    public ResponseEntity<?> charger(
//            @Validated @RequestBody ChargeSpotRequestDTO requestDTO
//            ){
//
//            chargerService.showCharger(requestDTO);
//
//
//
//
//
//
//
//    }





}
