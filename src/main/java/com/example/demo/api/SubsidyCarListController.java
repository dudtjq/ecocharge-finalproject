package com.example.demo.api;

import com.example.demo.service.SubsidyCarListService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/carList")
@Slf4j
public class SubsidyCarListController {
    
    private final SubsidyCarListService subSidyCarListService;
    
    @GetMapping()
    public ResponseEntity<?> carListRender() {
        log.info("/carList GET!");
        return ResponseEntity.ok().body(subSidyCarListService.findCarList());
    }
    
}
