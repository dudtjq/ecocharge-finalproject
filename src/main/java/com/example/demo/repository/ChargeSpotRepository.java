package com.example.demo.repository;

import com.example.demo.entity.ChargeSpot;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ChargeSpotRepository extends JpaRepository<ChargeSpot, Long> {
}
