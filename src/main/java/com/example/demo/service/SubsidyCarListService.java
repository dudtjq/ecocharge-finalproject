package com.example.demo.service;

import com.example.demo.entity.SubsidyCar;
import com.example.demo.repository.SubsidyCarListRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class SubsidyCarListService {
    
    private final SubsidyCarListRepository subsidyCarListRepository;
    
    public List<SubsidyCar> findCarList() {
        List<SubsidyCar> carList = subsidyCarListRepository.findAll();
        log.info("carList: {}", carList);
        return carList;
    }
}
