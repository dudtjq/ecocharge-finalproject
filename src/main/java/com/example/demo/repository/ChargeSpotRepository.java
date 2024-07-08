package com.example.demo.repository;

import com.example.demo.entity.ChargeSpot;
import com.example.demo.entity.Charger;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ChargeSpotRepository extends JpaRepository<ChargeSpot, String> {

    @Query("SELECT c, s FROM ChargeSpot c RIGHT JOIN c.statId s")
    List<ChargeSpot> searchCharger();


}
