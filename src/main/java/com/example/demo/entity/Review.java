package com.example.demo.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Getter @Setter @ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "tbl_review")
@Entity
public class Review {

    @Id
    @Column(name = "review_no")
//    @NotBlank
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long reviewNo;

    @Column(name = "r_content", scale = 1000)
    @NotBlank
    private String content;

    @CreationTimestamp
    private LocalDateTime reviewDate;

//    @CreationTimestamp
    private LocalDateTime updateReviewDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
//    @NotBlank
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "stat_id")
//    @NotBlank
    private ChargeSpot chargeSpot;

}
