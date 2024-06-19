package com.example.demo.api;

import com.example.demo.entity.CrawlingCar;
import com.example.demo.service.MainPageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/main")
public class MainPageController {
    
    private final MainPageService mainPageService;
    
    @GetMapping("/slider")
    public ResponseEntity<?> sliderRender() {
        return ResponseEntity.ok().body(mainPageService.sliderListFind());
    }
    
    @GetMapping("/qna")
    public ResponseEntity<?> qnaRender() {
        return ResponseEntity.ok().body(mainPageService.qnaListFind());
    }
    
    @GetMapping("/news")
    public ResponseEntity<?> newsRender() {
        return ResponseEntity.ok().body(mainPageService.newsListFind());
    }
    
}
