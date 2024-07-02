package com.example.demo.repository;

import com.example.demo.common.ItemWithSequence;
import com.example.demo.common.Page;
import com.example.demo.entity.SubsidyCar;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static com.example.demo.entity.QSubsidyCar.subsidyCar;

@RequiredArgsConstructor
public class SubsidyCarListRepositoryImpl implements SubsidyCarListRepositoryCustom {
    
    private final JPAQueryFactory jpaQueryFactory;
    
    @Override
    public List<SubsidyCar> findAll(Page page) {
        
        return jpaQueryFactory
                .selectFrom(subsidyCar)
                .offset(page.getPageStart())
                .limit(page.getAmount())
                .fetch();
    }
    
    @Override
    public List<SubsidyCar> findAllByKeyword(Page page, String search) {
        return jpaQueryFactory
                .selectFrom(subsidyCar)
                .where(subsidyCar.carName.toUpperCase().like("%" + search.toUpperCase() + "%"))
                .offset(page.getPageStart())
                .limit(page.getAmount())
                .fetch();
    }
    
    @Override
    public Long countByKeyword(String search) {
        return jpaQueryFactory
                .selectFrom(subsidyCar)
                .where(subsidyCar.carName.toUpperCase().like("%" + search.toUpperCase() + "%"))
                .stream().count();
    }
    
    
}
