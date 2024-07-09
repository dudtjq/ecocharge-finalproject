package com.example.demo.service;

import com.example.demo.dto.request.ChargeSpotRequestDTO;
import com.example.demo.dto.response.ChargeSpotMarkerResponsDTO;
import com.example.demo.dto.response.ChargerSpotResponseDTO;
import com.example.demo.entity.ChargeSpot;
import com.example.demo.repository.ChargeSpotRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class ChargeSpotService {
    
    private final ChargeSpotRepository chargeSpotRepository;
    
    
    public List<ChargeSpotMarkerResponsDTO> getMarker(double lat, double lng) {
        log.info("getMarker 동작!");
        List<ChargeSpot> chargeSpotList = chargeSpotRepository.findAll();
        
        List<ChargeSpotMarkerResponsDTO> spotList = new ArrayList<>();
                chargeSpotList.forEach((chargeSpot -> {
                    String latLng = chargeSpot.getLatLng();
                    String[] split = latLng.split(",");
                    
                    double epsilon = 0.02;


                    if (Math.abs(Double.parseDouble(split[0]) - lat) < epsilon) {
                        if (Math.abs(Double.parseDouble(split[1]) - lng) < epsilon) {
                            ChargeSpotMarkerResponsDTO dto = ChargeSpotMarkerResponsDTO.builder()
                                    .addr(chargeSpot.getAddr())
                                    .statNm(chargeSpot.getStatNm())
                                    .facilityBig(chargeSpot.getFacilityBig())
                                    .facilitySmall(chargeSpot.getFacilitySmall())
                                    .statId(chargeSpot.getStatId())
                                    .limitYn(chargeSpot.getLimitYn())
                                    .lat(split[0])
                                    .lng(split[1])
                                    .build();
                            
                            spotList.add(dto);
                        }
                    }
                }));
                
                log.info(spotList.toString());
        
        return spotList;
    }

    public List<ChargeSpot> findSearch(ChargeSpotRequestDTO requestDTO) {

        List<ChargeSpot> bySearch = chargeSpotRepository.findAll();

        List<ChargerSpotResponseDTO> responseDTO = new ArrayList<>();




        log.info(bySearch.toString());

        return bySearch;

    }
}
