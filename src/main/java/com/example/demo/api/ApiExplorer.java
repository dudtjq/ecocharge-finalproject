package com.example.demo.api;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
@SpringBootApplication
@Slf4j
public class ApiExplorer implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(ApiExplorer.class, args);
    }

    @Value("${chargeinfo.service.key}")
    private String serviceKey;

    @Override
    public void run(String... args) {
        try {

            String pageNo = "1";
            String numOfRows = "10";
            String zcode = "11";
//            String zscode = "11680";0
//            String statId = "ME183119";

            String url = "http://apis.data.go.kr/B552584/EvCharger/getChargerInfo" +
                    "?serviceKey=" + serviceKey +  //URLEncoder.encode(serviceKey, "UTF-8") +
                    "&pageNo=" + URLEncoder.encode(pageNo, "UTF-8") +
                    "&numOfRows=" + URLEncoder.encode(numOfRows, "UTF-8") +
                    "&zcode=" + URLEncoder.encode(zcode, "UTF-8");
//                    "&zscode=" + URLEncoder.encode(zscode, "UTF-8");
//                    "&statId=" + URLEncoder.encode(statId, "UTF-8");

            log.info("url : {}" , url);

            RestTemplate restTemplate = new RestTemplate();

            ResponseEntity<String> responseEntity = restTemplate.getForEntity(url, String.class);

          //  log.info("responseEntity : {}", responseEntity);
//            RestTemplate restTemplate = new RestTemplate();

            // 이미 인코딩된 서비스 키
//            String encodedServiceKey = "VkLtw1qBCYkLu+wcYQ3JJ9L9KS6vlrkDxSkrZiAVh4uK5k1+pfL8FHj1wwz717sGzAmJwkj7zsow6P/ojJdjQQ==";
//            String url = "http://apis.data.go.kr/B552584/EvCharger/getChargerInfo";

            // 인코딩된 키를 그대로 사용하여 URL 구성
//            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(url)
//                    .queryParam("serviceKey", encodedServiceKey)
//                    .queryParam("pageNo", 1)
//                    .queryParam("numOfRows", 10)
//                    .queryParam("zcode", 11);
//
//            String uri = builder.encode().toUriString();
//
//            log.info("uri : {}", uri);

            // HttpHeaders 객체 생성
//            HttpHeaders headers = new HttpHeaders();
//            HttpEntity<String> entity = new HttpEntity<>(headers);

            // GET 요청 보내기
//            ResponseEntity<String> response = restTemplate.exchange(uri, HttpMethod.GET, entity, String.class);
//            System.out.println(response.getBody());


            if (responseEntity.getStatusCode() == HttpStatus.OK) {
                log.info("Response code: {}" , responseEntity.getStatusCode());
                log.info("Response body: {}",  responseEntity.getBody());
            } else {
                log.info("Error occurred! Response code: {}" , responseEntity.getStatusCode());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}