//package com.example.demo.dto.request;
//
//import com.example.demo.entity.ChargeInfo;
//import kotlinx.datetime.LocalDateTime;
//import lombok.*;
//
//import java.util.Map;
//
//@Getter @ToString @Setter
//@EqualsAndHashCode @AllArgsConstructor
//@NoArgsConstructor
//@Builder
//public class ChargeInfoRequestDTO {
//
//    private String statId; // 충전소 Id
//
//    private String statNm; // 충전소 명
//
//    private String chgerId;
//
//    private String chgerType;
//
//    private String addr;
//
//    private String lat;
//
//    private String lng;
//
//    private String useTime;
//
//    private String busild;
//
//    private String busiNm;
//
//    private String stat;
//
//    private String powerType;
//
//    private String zcode;
//
//    private String parkingFree;
//
//    private String note;
//
//    public ChargeInfo toEntity(Map<String, String> requestDTO){
//
//        return ChargeInfo.builder()
//                .statId(Long.valueOf(statId))
//                .statNm(statNm)
//                .chgerId(Long.valueOf(chgerId))
//                .chgerType(Long.valueOf(chgerType))
//                .addr(addr)
//                .lat(Double.parseDouble(lat))
//                .lng(Double.parseDouble(lng))
//                .useTime(useTime)
//                .busild(busild)
//                .busiNm(busiNm)
//                .stat(Long.valueOf(stat))
//                .powerType(powerType)
//                .zcode(Long.valueOf(zcode))
//                .parkingFree(parkingFree)
//                .note(note)
//                .build();
//
//    }
//}
