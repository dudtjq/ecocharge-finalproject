package com.example.demo.api;

import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/tosspay")
public class PaymentController {
    @Value("${tosspay.secret_key}")
    private String SECRET_KEY;
    @Value("${tosspay.client_key}")
    private String CLIENT_KEY;
    private final RestTemplate restTemplate;

    @Autowired
    public PaymentController(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @RequestMapping(
            method = RequestMethod.POST,
            value = "/shop/confirm-payment"
    )
    public String confirmPayment(
            @RequestParam("customerKey") String customerKey,
            @RequestParam("paymentKey") String paymentKey,
            @RequestParam("orderId") String orderId,
            @RequestParam("amount") Long amount,
            Model model
    ) {
        HttpHeaders headers = new HttpHeaders();
        String authorization = Base64.getEncoder().encodeToString((SECRET_KEY + ":").getBytes());
        headers.set("Authorization", "Basic " + authorization);
        headers.setContentType(MediaType.APPLICATION_JSON);

        Map<String, String> payloadMap = new HashMap<>();
        payloadMap.put("customerKey", customerKey);
        payloadMap.put("paymentKey", paymentKey);
        payloadMap.put("orderId", orderId);
        payloadMap.put("amount", String.valueOf(amount));

        HttpEntity<Map<String, String>> request = new HttpEntity<>(payloadMap, headers);

        String confirmRequestUrl = "https://api.tosspayments.com/v1/brandpay/payments/confirm";
        ResponseEntity<JsonNode> responseEntity = restTemplate.postForEntity(
                confirmRequestUrl,
                request,
                JsonNode.class
        );

        if (responseEntity.getStatusCode() == HttpStatus.OK) {
            JsonNode successNode = responseEntity.getBody();
            // 커스텀 모델에 attribute 추가
            model.addAttribute("orderId", successNode.get("orderId").asText());
            return "success";
        } else {
            JsonNode failNode = responseEntity.getBody();
            model.addAttribute("message", failNode.get("message").asText());
            model.addAttribute("code", failNode.get("code").asText());
            return "fail";
        }
    }
}
