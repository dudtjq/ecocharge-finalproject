package com.example.demo.repository;

import com.example.demo.entity.Qna;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;


public interface QnaRepository extends JpaRepository<Qna, Long>, QnaRepositoryCustom {

}
