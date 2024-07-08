package com.example.demo.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.cglib.core.Local;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter @Setter @ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "tbl_reservation")
@Builder
public class Reservation {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String reservationNo;

    @Enumerated(value = EnumType.STRING)
    private RSTATUS rStatus;

    @CreationTimestamp
    private LocalDateTime date;

    @CreationTimestamp
    private LocalDateTime reservationDate;

    @NotBlank
    private String startTime;

    @NotBlank
    private String finishTime;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "stat_id")
    private ChargeSpot chargeSpot;

    public enum RSTATUS {
        AVAILABLE, USE
    }


}
