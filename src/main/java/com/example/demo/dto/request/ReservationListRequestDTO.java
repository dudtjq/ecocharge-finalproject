package com.example.demo.dto.request;

import com.example.demo.entity.Reservation;
import com.example.demo.entity.User;
import lombok.*;

@Setter @Getter @ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReservationListRequestDTO {

    private String userId;

    public Reservation toEntity(User user) {
        return Reservation.builder()
                .user(user)
                .build();
    }

}
