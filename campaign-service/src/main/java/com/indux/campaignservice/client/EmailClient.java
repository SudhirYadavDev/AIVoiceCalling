package com.indux.campaignservice.client;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@Component
public class EmailClient {

    private final RestTemplate restTemplate;

    public EmailClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public void sendEmail(String to, String subject, String body) {

        String url = "http://localhost:8083/api/email/send";

        Map<String, String> request = new HashMap<>();
        request.put("to", to);
        request.put("subject", subject);
        request.put("body", body);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<Map<String, String>> entity = new HttpEntity<>(request, headers);

        restTemplate.postForObject(url, entity, String.class);
    }
}
