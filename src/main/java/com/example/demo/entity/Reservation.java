package com.example.demo.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.cglib.core.Local;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter @Setter @ToString
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "tbl_reservation")
@Builder
public class Reservation {

    @Id
    @NotBlank
    @GeneratedValue(strategy = GenerationType.UUID)
    private String reservationNo;

    @NotBlank
    private RSTATUS rStatus;

    @NotBlank
    private LocalDateTime date;

    @NotBlank
    @CreationTimestamp
    private LocalDateTime reservationDate;

    @NotBlank
    @CreationTimestamp
    private String startTime;

    @NotBlank
    private String finishTime;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "stat_id")
    private ChargeSpot chargeSpot;

    public static enum RSTATUS {
        AVAILABLE, USE
    }

}
