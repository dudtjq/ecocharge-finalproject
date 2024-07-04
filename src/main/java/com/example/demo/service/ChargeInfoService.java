//package com.example.demo.service;
//
////import com.example.demo.dto.request.ChargeInfoRequestDTO;
//import com.example.demo.entity.ChargeInfo;
//import com.example.demo.repository.ChargeInfoRepository;
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.stereotype.Service;
//
//import java.util.Map;
//
//@Service
//@Slf4j
//@RequiredArgsConstructor
//public class ChargeInfoService {
//
//    private final ChargeInfoRepository chargeInfoRepository;
//
//    public ChargeInfo saveChargeInfo(Map<String, String> requestDTO){
//        final ChargeInfo chargeInfo = ChargeInfo.builder()
//                .lng(Double.parseDouble(requestDTO.get("lng")))
//                .zcode(Long.valueOf(requestDTO.get("zcode")))
//                .lat(Double.parseDouble(requestDTO.get("lat")))
//                .note(requestDTO.get("note"))
//                .addr(requestDTO.get("addr"))
//                .stat(Long.valueOf(requestDTO.get("stat")))
//                .busiId(requestDTO.get("busiId"))
//                .statNm(requestDTO.get("statNm"))
//                .statId(Long.valueOf(requestDTO.get("statId")))
//                .chgerId(Long.valueOf(requestDTO.get("chgerId")))
//                .chgerType(Long.valueOf(requestDTO.get("chgerType")))
//                .useTime(requestDTO.get("useTime"))
//                .busiNm(requestDTO.get("busiNm"))
//                .parkingFree(requestDTO.get("parkingFree"))
//                .powerType(requestDTO.get("powerType"))
//                .limitYn(requestDTO.get("limitYn"))
//                .limitDetail(requestDTO.get("limitDetail"))
//                .output(Long.valueOf(requestDTO.get("output")))
//                .method(requestDTO.get("method"))
//                .build();
//
//        return chargeInfoRepository.save(chargeInfo);
//
//    }
//
//
//}
