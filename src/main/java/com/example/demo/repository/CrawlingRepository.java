package com.example.demo.repository;

import com.example.demo.entity.CrawlingCar;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CrawlingRepository extends JpaRepository<CrawlingCar, String> {
}
