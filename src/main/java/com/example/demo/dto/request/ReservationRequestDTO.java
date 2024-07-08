package com.example.demo.dto.request;

import com.example.demo.entity.ChargeSpot;
import com.example.demo.entity.Reservation;
import com.example.demo.entity.User;
import lombok.*;

@Getter @Setter @ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReservationRequestDTO {

    private String reservationNo;
    private Reservation.RSTATUS rStatus;
    private String startTime;
    private String finishTime;
    private String userId;
    private String statId;

    public Reservation toEntity(User user, ChargeSpot chargeSpot) {
        return Reservation.builder()
                .reservationNo(reservationNo)
                .rStatus(rStatus)
                .startTime(startTime)
                .finishTime(finishTime)
                .user(user)
                .chargeSpot(chargeSpot)
                .build();
    }

}
