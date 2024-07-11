package com.example.demo.dto.response;

import com.example.demo.entity.ChargeSpot;
import com.example.demo.entity.Reservation;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import java.time.LocalDateTime;
import java.util.Date;

@Getter @Setter @ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class ReservationResponseDTO {

    private String reservationNo;
    private String rStatus;

    @JsonFormat(pattern = "yyyy년 MM월 dd일 HH:mm")
    private LocalDateTime date;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDateTime reservationDate;

    private String startTime;
    private String finishTime;
    private String userId;
    private String statNm;

    public ReservationResponseDTO(Reservation reservation) {
        this.reservationNo = reservation.getReservationNo();
        this.rStatus = mapStatus(reservation.getRStatus());
        this.date = reservation.getDate();
        this.reservationDate = reservation.getReservationDate();
        this.startTime = reservation.getStartTime();
        this.finishTime = reservation.getFinishTime();
        this.userId = reservation.getUser().getUserId();
        this.statNm = reservation.getChargeSpot().getStatNm();
    }

    private String mapStatus (Reservation.RSTATUS rStatus) {
        switch (rStatus) {
            case USE:
                return "예약중";
            case AVAILABLE:
                return "사용완료";
            default:
                return "알 수 없음";
        }
    }
}
