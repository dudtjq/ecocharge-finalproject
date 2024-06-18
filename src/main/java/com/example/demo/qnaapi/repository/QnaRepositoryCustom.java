package com.example.demo.qnaapi.repository;

import com.example.demo.qnaapi.entity.Qna;

import java.util.List;

public interface QnaRepositoryCustom {

    List<Qna> QnaAllList();

    List<Qna> QnaByTitle(String qTitle);

    List<Qna> QnaByContent(String qContent);

    List<Qna> QnaByNumber(Long qnaNo);

    List<Qna> QnaByAnswer(String qAnswer);

}
