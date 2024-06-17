package com.example.demo.qnaapi;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.util.List;

import static com.example.demo.qnaapi.QQna.*;

@RequiredArgsConstructor
public class QnaRepositoryImpl implements QnaRepositoryCustom{

    private final JPAQueryFactory queryFactory;

    @Override
    public List<Qna> QnaAllList() {
        return queryFactory
                .selectFrom(qna)
                .fetch();
    }

    @Override
    public List<Qna> QnaByTitle(String qTitle) {
        return null;
    }

    @Override
    public List<Qna> QnaByContent(String qContent) {

        return null;
    }

    @Override
    public List<Qna> QnaByNumber(Long qnaNo) {

        return null;
    }

    @Override
    public List<Qna> QnaByAnswer(String qAnswer) {
        return null;
    }


}
