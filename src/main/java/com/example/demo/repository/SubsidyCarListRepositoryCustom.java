package com.example.demo.repository;

import com.example.demo.common.Page;
import com.example.demo.entity.SubsidyCar;

import java.util.List;

public interface SubsidyCarListRepositoryCustom {
    
    List<SubsidyCar> findAll(Page page);
    
    List<SubsidyCar> findAllByKeyword(Page page, String search);
    
    Long countByKeyword(String search);
}
