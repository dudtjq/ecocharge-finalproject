package com.example.demo.repository;

import com.example.demo.entity.Qna;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


public interface QnaRepository extends JpaRepository<Qna, Long> {


    void deleteByQnaNo(Long qnaNo);
}
