package com.example.demo.service;

import com.example.demo.common.Page;
import com.example.demo.common.PageMaker;
import com.example.demo.dto.response.SubsidyCarResponseDTO;
import com.example.demo.entity.SubsidyCar;
import com.example.demo.repository.SubsidyCarListRepository;
import com.example.demo.repository.SubsidyCarListRepositoryImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class SubsidyCarListService {
    
    private final SubsidyCarListRepository subsidyCarListRepository;
    private final SubsidyCarListRepositoryImpl subsidyCarListRepositoryImpl;
    
    public SubsidyCarResponseDTO findCarList(int pageNo) {
        long count = subsidyCarListRepository.count();
        Page page = new Page();
        page.setPageNo(pageNo);
        List<SubsidyCar> carList = subsidyCarListRepositoryImpl.findAll(page);
        PageMaker pageMaker = new PageMaker(page, (int) count);
//        log.info("carList: {}", carList);
        
        SubsidyCarResponseDTO dto = SubsidyCarResponseDTO
                .builder()
                .subsidyCarList(carList)
                .pageMaker(pageMaker)
                .build();
        
        log.info("dto: {}", dto);
        
        return dto;
    }
    
}
