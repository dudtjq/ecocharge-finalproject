package com.example.demo.service;

//import com.example.demo.dto.request.ChargeInfoRequestDTO;
import com.example.demo.entity.ChargeSpot;
import com.example.demo.repository.ChargeSpotRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@Slf4j
@RequiredArgsConstructor
public class ChargeInfoService {

    private final ChargeSpotRepository chargeInfoRepository;

    public ChargeSpot saveChargeInfo(Map<String, String> requestDTO){
        final ChargeSpot chargeInfo = ChargeSpot.builder()
                .addr(requestDTO.get("addr"))
                .statNm(requestDTO.get("statNm"))
                .statId((requestDTO.get("statId")))
                .limitYn(requestDTO.get("limitYn"))
                .build();

        return chargeInfoRepository.save(chargeInfo);

    }


}
