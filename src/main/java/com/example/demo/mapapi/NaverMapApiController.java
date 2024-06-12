package com.example.demo.mapapi;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class NaverMapApiController {

    @GetMapping("/findmap")
    public String naverMap(){
        return "naver-map";
    }

}
