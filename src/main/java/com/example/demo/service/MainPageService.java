package com.example.demo.service;

import com.example.demo.entity.CrawlingCar;
import com.example.demo.entity.News;
import com.example.demo.entity.Qna;
import com.example.demo.repository.CrawlingRepository;
import com.example.demo.repository.NewsRepository;
import com.example.demo.repository.QnaRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class MainPageService {
    
    private final CrawlingRepository crawlingRepository;
    private final NewsRepository newsRepository;
    private final QnaRepository qnaRepository;
    
    public List<CrawlingCar> sliderListFind() {
        return crawlingRepository.findAll();
    }
    
    public List<Qna> qnaListFind() {
        return qnaRepository.findAll().subList(0, 3);
    }
    
    public List<News> newsListFind() {
        return newsRepository.findAll().subList(0, 4);
    }
}
