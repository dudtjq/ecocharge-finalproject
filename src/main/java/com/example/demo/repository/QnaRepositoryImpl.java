package com.example.demo.repository;

import com.example.demo.common.ItemWithSequence;
import com.example.demo.common.Page;
import com.example.demo.entity.Qna;
import com.example.demo.entity.User;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static com.example.demo.entity.QQna.qna;

@RequiredArgsConstructor
@Slf4j
@ToString
public class QnaRepositoryImpl implements QnaRepositoryCustom {

    private final JPAQueryFactory jpaQueryFactory;

    @PersistenceContext
    private EntityManager em;


    @Override
    public List<ItemWithSequence> findAll(Page page) {
        log.info("findAll");
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

    @Override
    @Transactional
    public List<ItemWithSequence> findAllByUser(Page page, User user) {
        log.info("findAllByUser:{}",user);
        List<Qna> items = em.createQuery("SELECT q FROM Qna q WHERE q.user = :user ORDER BY q.createDate DESC", Qna.class)
                .setParameter("user", user)
                .setFirstResult(page.getPageStart())
                .setMaxResults(page.getAmount())
                .getResultList();

        return IntStream.range(0, items.size())
                .mapToObj(i -> new ItemWithSequence(i + 1 + page.getPageStart(), items.get(i)))
                .collect(Collectors.toList());
    }
}
