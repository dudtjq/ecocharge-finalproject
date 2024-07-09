package com.example.demo.repository;

import com.example.demo.dto.response.ChargerSpotResponseDTO;
import com.example.demo.entity.ChargeSpot;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

public interface ChargeSpotRepository extends JpaRepository<ChargeSpot, String> {

    @Query("SELECT s, c FROM ChargeSpot c RIGHT JOIN Charger s ON s.chargeSpot.statId = c.statId WHERE s.chgerType = :chgerType AND s.powerType = :powerType AND c.limitYn = :limitYn")
    List<ChargeSpot> findAllBySearch(@Param("chgerType") String chgerType, @Param("powerType") String powerType, @Param("limitYn") String limitYn);

    // @Query("SELECT c, s FROM ChargeSpot c RIGHT JOIN c.statId s")
  // List<ChargeSpot> searchCharger();




}
