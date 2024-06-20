package com.example.demo.repository;

import com.example.demo.entity.SubsidyCar;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SubsidyCarListRepository extends JpaRepository<SubsidyCar, String> {
}
