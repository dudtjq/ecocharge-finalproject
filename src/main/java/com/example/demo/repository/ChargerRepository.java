package com.example.demo.repository;

import com.example.demo.entity.Charger;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ChargerRepository extends JpaRepository<Charger,String> {

//    @Query("SELECT c, s FROM Charger c LEFT JOIN c.statId s")
//    List<Charger> searchCharger();


}
