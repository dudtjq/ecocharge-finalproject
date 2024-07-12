package com.example.demo.repository;

import com.example.demo.common.ItemWithSequence;
import com.example.demo.common.Page;
import com.example.demo.entity.Board;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static com.example.demo.entity.QBoard.board;

@RequiredArgsConstructor
public class BoardRepositoryImpl implements BoardRepositoryCustom {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public List<ItemWithSequence> findAll(Page page) {

        List<Board> items = jpaQueryFactory
                .selectFrom(board)
                .offset(page.getPageStart())
                .limit(page.getAmount())
                .orderBy(board.createDate.desc())
                .fetch();

        return IntStream.range(0, items.size())
                .mapToObj(i -> new ItemWithSequence(i + 1 +(page.getPageStart()), items.get(i)))
                .collect(Collectors.toList());
    }
}
