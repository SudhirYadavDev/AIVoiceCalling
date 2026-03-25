package com.indux.campaignservice.client;

import com.indux.campaignservice.dto.LeadDTO;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Component
public class LeadClient {

    private final RestTemplate restTemplate;

    public LeadClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public List<LeadDTO> getAllLeads() {

        String url = "http://localhost:8081/api/leads";

        return restTemplate.exchange(
                url,
                org.springframework.http.HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<LeadDTO>>() {}
        ).getBody();
    }
}