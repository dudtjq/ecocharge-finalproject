package com.example.demo.repository;

import com.example.demo.common.Page;
import com.example.demo.dto.response.SubsidyCarResponseDTO;
import com.example.demo.entity.SubsidyCar;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.util.List;

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
}
