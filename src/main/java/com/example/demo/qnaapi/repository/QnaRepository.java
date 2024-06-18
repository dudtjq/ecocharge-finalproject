package com.example.demo.qnaapi.repository;

import com.example.demo.qnaapi.entity.Qna;
import org.springframework.data.jpa.repository.JpaRepository;


public interface QnaRepository extends JpaRepository<Qna, Long>, QnaRepositoryCustom {


}
