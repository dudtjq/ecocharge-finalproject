package com.example.demo.service;

import com.example.demo.dto.request.ReservationListRequestDTO;
import com.example.demo.dto.request.ReservationRequestDTO;
import com.example.demo.dto.response.ReservationResponseDTO;
import com.example.demo.entity.ChargeSpot;
import com.example.demo.entity.Reservation;
import com.example.demo.entity.User;
import com.example.demo.repository.ChargeSpotRepository;
import com.example.demo.repository.ReservationRepository;
import com.example.demo.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class ReservationService {

    private final ReservationRepository reservationRepository;
    private final UserRepository userRepository;
    private final ChargeSpotRepository chargeSpotRepository;

    public void create(final ReservationRequestDTO requestDTO) {
        User user = userRepository.findById(requestDTO.getUserId())
                .orElseThrow(() -> new RuntimeException("사용자를 찾을 수 없습니다."));
        ChargeSpot chargeSpot = chargeSpotRepository.findById(requestDTO.getStatId())
                .orElseThrow(() -> new RuntimeException("충전소를 찾을 수 없습니다."));
        requestDTO.setRStatus(Reservation.RSTATUS.USE);
        reservationRepository.save(requestDTO.toEntity(user, chargeSpot));
    }

    public boolean delete(final String id) {
        Reservation reservation = reservationRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("예약이 존재하지 않습니다.");
                    throw new RuntimeException("예약이 존재하지 않습니다.");
                }
        );
        reservationRepository.delete(reservation);
        return true;
    }

    public List<ReservationResponseDTO> retrieve(final ReservationListRequestDTO dto) {
        List<Reservation> reservationList = reservationRepository.findByUser_UserId(dto.getUserId());
        LocalDate today = LocalDate.now();

        for (Reservation reservation : reservationList) {
            if (reservation.getReservationDate().toLocalDate().isAfter(today)) {
                reservation.setRStatus(Reservation.RSTATUS.AVAILABLE);
                reservationRepository.save(reservation);
            }
        }
        log.info("reservationList: {}", reservationList);
        List<ReservationResponseDTO> dtoList = reservationList.stream()
                .map(ReservationResponseDTO::new)
                .collect(Collectors.toList());
        return dtoList;
    }
}
