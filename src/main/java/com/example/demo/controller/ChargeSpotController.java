package com.example.demo.controller;

import com.example.demo.dto.request.ChargeSpotRequestDTO;
import com.example.demo.entity.ChargeSpot;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequestMapping("/chargespot")
public class ChargeSpotController {

    @PostMapping
    public ResponseEntity<?> crateChargeSpot(
            @Validated @RequestBody ChargeSpotRequestDTO requestDTO
            ){

    }





}
