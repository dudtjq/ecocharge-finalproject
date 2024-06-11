package com.eco.chap.api;

<<<<<<< HEAD:src/main/java/com/example/demo/api/ApiExplorer.java
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
=======
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
>>>>>>> fd868d55579e5609abe20e34ba2828e05b27a126:src/main/java/com/eco/chap/api/ApiExplorer.java

@SpringBootApplication
public class ApiExplorer implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(ApiExplorer.class, args);
    }

    @Override
    public void run(String... args) {
        try {
            String serviceKey = "wxNd53XeLl8o0O7cDWKZgw08DLradmwhjTRGZN%2B2H%2BjbKkatBGYQDlaZWSFOEpp5AP4TM6YtoVbF8Rw72wJPkA%3D%3D";
            String pageNo = "1";
            String numOfRows = "10";
            String period = "5";
            String zcode = "11";

            String url = "http://apis.data.go.kr/B552584/EvCharger/getChargerStatus" +
                    "?serviceKey=" + URLEncoder.encode(serviceKey, "UTF-8") +
                    "&pageNo=" + URLEncoder.encode(pageNo, "UTF-8") +
                    "&numOfRows=" + URLEncoder.encode(numOfRows, "UTF-8") +
                    "&period=" + URLEncoder.encode(period, "UTF-8") +
                    "&zcode=" + URLEncoder.encode(zcode, "UTF-8");

            RestTemplate restTemplate = new RestTemplate();
            ResponseEntity<String> responseEntity = restTemplate.getForEntity(url, String.class);

            if (responseEntity.getStatusCode() == HttpStatus.OK) {
                System.out.println("Response code: " + responseEntity.getStatusCode());
                System.out.println("Response body: " + responseEntity.getBody());
            } else {
                System.out.println("Error occurred! Response code: " + responseEntity.getStatusCode());
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }
}