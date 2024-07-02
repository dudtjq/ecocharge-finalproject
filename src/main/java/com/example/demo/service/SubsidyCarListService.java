package com.example.demo.service;

import com.example.demo.common.ItemWithSequence;
import com.example.demo.common.Page;
import com.example.demo.common.PageMaker;
import com.example.demo.dto.response.SubsidyCarResponseDTO;
import com.example.demo.entity.SubsidyCar;
import com.example.demo.repository.SubsidyCarListRepository;
import com.example.demo.repository.SubsidyCarListRepositoryImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class SubsidyCarListService {
    
    private final SubsidyCarListRepository subsidyCarListRepository;
    private final SubsidyCarListRepositoryImpl subsidyCarListRepositoryImpl;
    
    public SubsidyCarResponseDTO findCarList(int pageNo, String search) {
        long count;
        Page page = new Page();
        page.setPageNo(pageNo);
        log.info("search: {}", search);
        List<SubsidyCar> carList = new ArrayList<>();
        if (search.trim().isEmpty()) {
            carList = subsidyCarListRepositoryImpl.findAll(page);
            count = subsidyCarListRepository.count();
        } else {
            carList = subsidyCarListRepositoryImpl.findAllByKeyword(page, search);
            count = subsidyCarListRepositoryImpl.countByKeyword(search);
        }
        PageMaker pageMaker = new PageMaker(page, (int) count);
        log.info("carList: {}", carList);
        
        SubsidyCarResponseDTO dto = SubsidyCarResponseDTO
                .builder()
                .subsidyCarList(carList)
                .pageMaker(pageMaker)
                .build();
        
//        log.info("dto: {}", dto);
        
        return dto;
    }
    
}
