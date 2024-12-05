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
 public String getStockData(
        @RequestParam(required = false) String symbol,
        Model model) {
    model.addAttribute("symbol", symbol);
    
    if (symbol != null && !symbol.isEmpty()) {
        try {
            // Get both 1D and 1M data
            String intradayData = alphaVantageService.getStockData(symbol, "1D");
            String monthlyData = alphaVantageService.getStockData(symbol, "1M");
            
            // Process intraday data for chart (existing logic)
            ObjectMapper mapper = new ObjectMapper();
            JsonNode intradayRoot = mapper.readTree(intradayData);
            JsonNode timeSeriesData = intradayRoot.get("Time Series (5min)");
            
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
            
            // Process monthly data for analysis
            JsonNode monthlyRoot = mapper.readTree(monthlyData);
            JsonNode monthlySeriesData = monthlyRoot.get("Monthly Time Series");
            
            // Calculate performance metrics
            Map<String, Object> analysis = calculatePerformanceMetrics(monthlySeriesData);
            
            model.addAttribute("stockDataMap", stockDataMap);
            model.addAttribute("analysis", analysis);
            
        } catch (Exception e) {
            model.addAttribute("error", "Error fetching stock data: " + e.getMessage());
        }
    }
    return "data";
}

private Map<String, Object> calculatePerformanceMetrics(JsonNode monthlyData) {
    Map<String, Object> analysis = new HashMap<>();
    
    if (monthlyData != null && monthlyData.isObject()) {
        List<Double> monthlyReturns = new ArrayList<>();
        Double previousClose = null;
        
        // Calculate monthly returns
        Iterator<Map.Entry<String, JsonNode>> fields = monthlyData.fields();
        while (fields.hasNext()) {
            Map.Entry<String, JsonNode> entry = fields.next();
            Double currentClose = Double.parseDouble(entry.getValue().get("4. close").asText());
            
            if (previousClose != null) {
                double monthlyReturn = (currentClose - previousClose) / previousClose;
                monthlyReturns.add(monthlyReturn);
            }
            previousClose = currentClose;
        }
        
        // Calculate average return and volatility
        double averageReturn = monthlyReturns.stream()
            .mapToDouble(Double::doubleValue)
            .average()
            .orElse(0.0);
        
        double volatility = Math.sqrt(monthlyReturns.stream()
            .mapToDouble(r -> Math.pow(r - averageReturn, 2))
            .average()
            .orElse(0.0));
        
        // Annualized expected return
        double annualizedReturn = averageReturn * 12 * 100; // in percentage
        
        // Determine risk level based on volatility
        String riskLevel;
        if (volatility < 0.05) {
            riskLevel = "LOW";
        } else if (volatility < 0.15) {
            riskLevel = "MEDIUM";
        } else {
            riskLevel = "HIGH";
        }
        
        analysis.put("expectedReturn", Double.valueOf(annualizedReturn));
        analysis.put("riskLevel", riskLevel);
    }
    
    return analysis;
}
}