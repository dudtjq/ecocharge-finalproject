package com.example.demo.qnaapi;

import org.springframework.data.jpa.repository.JpaRepository;


public interface QnaRepository extends JpaRepository<Qna, Long>, QnaRepositoryCustom {


}