package com.stock.microservice.project.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.client.RestClientException;

import java.util.Map;
import java.util.HashMap;

@Service
public class AlphaVantageService {
    @Value("${alphavantage.api.key}")
    private String apiKey;

    private static final String API_URL = "https://www.alphavantage.co/query?function=TIME_SERIES_INTRADAY&symbol={symbol}&interval=5min&apikey={apikey}";

    public String getStockData(String symbol) {
        try {
            RestTemplate restTemplate = new RestTemplate();
            
            Map<String, String> params = new HashMap<>();
            params.put("symbol", symbol);
            params.put("apikey", apiKey);
            
            ResponseEntity<String> response = restTemplate.getForEntity(API_URL, String.class, params);
            
            if (response.getStatusCode() == HttpStatus.OK) {
                String body = response.getBody();
                
                if (body != null && body.contains("Error Message")) {
                    throw new RuntimeException("API Error: Invalid stock symbol");
                }
                
                return body;
            } else {
                throw new RuntimeException("Failed to fetch data: " + response.getStatusCode());
            }
            
        } catch (RestClientException e) {
            throw new RuntimeException("API service error: " + e.getMessage());
        } catch (Exception e) {
            throw new RuntimeException("Error processing request: " + e.getMessage());
        }
    }
}
