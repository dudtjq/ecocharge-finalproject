package com.example.demo.repository;

import com.example.demo.entity.Qna;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;


public interface QnaRepository extends JpaRepository<Qna, Long>, QnaRepositoryCustom {

    // qna 목록 리턴

    @Query("SELECT q FROM Qna q")
    List<Qna> findAll();





}
