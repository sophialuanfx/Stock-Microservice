package com.stock.microservice.project.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
// import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.stock.microservice.project.service.AlphaVantageService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.JsonNode;
import java.util.*;

@Controller
@RequestMapping("/page")
public class StockController {

    @Autowired
    private AlphaVantageService alphaVantageService;
 @GetMapping("/data")
 public String getStockData(@RequestParam(required = false) String symbol, Model model) {
        model.addAttribute("symbol", symbol);
        
        if (symbol != null && !symbol.isEmpty()) {
            try {
                String stockData = alphaVantageService.getStockData(symbol);
                
                // Parse JSON response
                ObjectMapper mapper = new ObjectMapper();
                JsonNode rootNode = mapper.readTree(stockData);
                JsonNode timeSeriesData = rootNode.get("Time Series (5min)");
                
                // Convert to a more view-friendly format
                Map<String, Map<String, String>> stockDataMap = new TreeMap<>(Collections.reverseOrder());
                if (timeSeriesData != null && timeSeriesData.isObject()) {
                    Iterator<Map.Entry<String, JsonNode>> fields = timeSeriesData.fields();
                    while (fields.hasNext()) {
                        Map.Entry<String, JsonNode> entry = fields.next();
                        Map<String, String> values = new HashMap<>();
                        values.put("open", entry.getValue().get("1. open").asText());
                        values.put("high", entry.getValue().get("2. high").asText());
                        values.put("low", entry.getValue().get("3. low").asText());
                        values.put("close", entry.getValue().get("4. close").asText());
                        values.put("volume", entry.getValue().get("5. volume").asText());
                        stockDataMap.put(entry.getKey(), values);
                    }
                }
                
                model.addAttribute("stockDataMap", stockDataMap);
                
            } catch (Exception e) {
                model.addAttribute("error", "Error fetching stock data: " + e.getMessage());
            }
        }
        return "data";
    }
}