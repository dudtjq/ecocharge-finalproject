package com.example.demo.dto.response;

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
    private Reservation.RSTATUS rStatus;

    @JsonFormat(pattern = "yyyy년 MM월 dd일 HH:mm")
    private LocalDateTime date;

    @JsonFormat(pattern = "yy년 MM월 dd일")
    private LocalDateTime reservationDate;

    private String startTime;
    private String finishTime;
    private String userId;
    private String statId;

    public ReservationResponseDTO(Reservation reservation) {
        this.reservationNo = reservation.getReservationNo();
        this.rStatus = reservation.getRStatus();
        this.date = reservation.getDate();
        this.reservationDate = reservation.getReservationDate();
        this.startTime = reservation.getStartTime();
        this.finishTime = reservation.getFinishTime();
        this.userId = reservation.getUser().getUserId();
        this.statId = reservation.getChargeSpot().getStatId();
    }
}
