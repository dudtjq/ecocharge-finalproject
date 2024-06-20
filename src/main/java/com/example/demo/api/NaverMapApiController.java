package com.example.demo.api;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.Mapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.StandardCharsets;
import java.util.List;

@Controller
@RequestMapping("/naver")
@Slf4j
public class NaverMapApiController {

    @Value("${navermap.client_id}")
    private String NAVERMAP_CLIENT_ID;

    @Value("${navermap.client_secret}")
    private String NAVERMAP_CLIENT_SECRET;

    @PostMapping("/map")
    public ResponseEntity<?> main() {
        // API 호출에 필요한 파라미터 설정
        String baseUrl = "https://naveropenapi.apigw.ntruss.com/map-static/v2/raster";
        String center = "127.1054221,37.3591614";
        String crs = "EPSG:4326";
        int level = 16;
        int width = 300;
        int height = 300;
        String lang = "ko";
        String clientId = NAVERMAP_CLIENT_ID;

        // API 호출 URL 생성
        String apiUrl = baseUrl + "?crs=" + crs + "&w=" + width + "&h=" + height + "&center=" + center + "&level=" + level + "&lang=" + lang + "&format=jpg" + "&X-NCP-APIGW-API-KEY-ID=" + clientId;

        RestTemplate restTemplate = new RestTemplate();

        // API 호출 시 필요한 헤더 설정
        HttpHeaders headers = new HttpHeaders();
        headers.set("X-NCP-APIGW-API-KEY-ID", NAVERMAP_CLIENT_ID);
        headers.set("X-NCP-APIGW-API-KEY", NAVERMAP_CLIENT_SECRET);

        HttpEntity<String> entity = new HttpEntity<>(headers);

        // API 호출 및 응답 수신
        ResponseEntity<String> response = restTemplate.exchange(apiUrl, HttpMethod.GET, entity, String.class);

        // 응답 확인
        if (response.getStatusCode() == HttpStatus.OK) {
            String responseBody = response.getBody();
            log.info("API 응답: {}", responseBody);
        } else {
            log.error("API 호출에 실패했습니다. 상태 코드: {}", response.getStatusCodeValue());
        }

        try {
            return ResponseEntity.ok().body(response);
        }catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }

    }
}


