package com.eco.chap.api;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class ApiExplorer {
    public static void main(String[] args) throws IOException, InterruptedException {
        StringBuilder urlBuilder = new StringBuilder("http://apis.data.go.kr/B552584/EvCharger/getChargerInfo"); /*URL*/
        urlBuilder.append("?" + "serviceKey" + "=서비스키"); /*Service Key*/
        urlBuilder.append("&" + "pageNo" + "=" + "1"); /*페이지번호*/
        urlBuilder.append("&" + "numOfRows" + "=" + "10"); /*한 페이지 결과 수 (최소 10, 최대 9999)*/
        urlBuilder.append("&" + "zcode" + "=" + "11"); /*시도 코드 (행정구역코드 앞 2자리)*/

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(urlBuilder.toString()))
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        System.out.println("Response code: " + response.statusCode());
        System.out.println(response.body());
    }
}
