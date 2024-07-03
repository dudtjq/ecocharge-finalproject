package com.example.demo.repository;

import com.example.demo.common.ItemWithSequence;
import com.example.demo.common.Page;
import com.example.demo.entity.Qna;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static com.example.demo.entity.QBoard.board;
import static com.example.demo.entity.QQna.qna;

@RequiredArgsConstructor
public class QnaRepositoryImpl implements QnaRepositoryCustom {
    
    private final JPAQueryFactory jpaQueryFactory;
    @Override
    public List<ItemWithSequence> findAll(Page page) {
        List<Qna> items = jpaQueryFactory
                .selectFrom(qna)
                .offset(page.getPageStart())
                .limit(page.getAmount())
                .orderBy(qna.createDate.desc())
                .fetch();
        
        return IntStream.range(0, items.size())
                .mapToObj(i -> new ItemWithSequence(i + 1 +(page.getPageStart()), items.get(i)))
                .collect(Collectors.toList());
    }
}
