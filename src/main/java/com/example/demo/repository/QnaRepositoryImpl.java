package com.example.demo.repository;

import com.example.demo.entity.Qna;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.util.List;

import static com.example.demo.entity.QQna.qna;


@RequiredArgsConstructor
public class QnaRepositoryImpl implements QnaRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    // 전체 조회
    @Override
    public List<Qna> QnaAllList() {
        return queryFactory
                .selectFrom(qna)
                .fetch();
    }

    // 제목 조회
    @Override
    public List<Qna> QnaByTitle(String qTitle) {
        return queryFactory
                .selectFrom(qna)
                .where(qna.qTitle.eq(qTitle))
                .fetch();
    }

    // 제목 조회
    @Override
    public List<Qna> QnaByContent(String qContent) {

        return queryFactory
                .selectFrom(qna)
                .where(qna.qContent.eq(qContent))
                .fetch();
    }

    @Override
    public List<Qna> QnaByNumber(Long qnaNo) {
        return queryFactory
                .selectFrom(qna)
                .where(qna.qnaNo.eq(qnaNo))
                .fetch();
    }

    @Override
    public List<Qna> QnaByAnswer(String qAnswer) {
        return queryFactory
                .selectFrom(qna)
                .where(qna.qAnswer.eq(qAnswer))
                .fetch();
    }


}
