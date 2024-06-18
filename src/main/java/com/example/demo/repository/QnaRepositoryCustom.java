package com.example.demo.repository;

import com.example.demo.entity.Qna;

import java.util.List;

public interface QnaRepositoryCustom {

    List<Qna> QnaAllList();

    List<Qna> QnaByTitle(String qTitle);

    List<Qna> QnaByContent(String qContent);

    List<Qna> QnaByNumber(Long qnaNo);

    List<Qna> QnaByAnswer(String qAnswer);

}
