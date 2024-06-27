package com.example.demo.repository;

import com.example.demo.entity.ChargeInfo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChargeInfoRepository extends JpaRepository<ChargeInfo, Long> {
}
