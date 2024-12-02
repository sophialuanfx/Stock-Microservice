package com.stock.microservice.project.controller;

import com.stock.microservice.project.service.AlphaVantageService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(StockController.class)
public class StockControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AlphaVantageService alphaVantageService;

    @Test
    void getStockData_NoSymbol_ReturnsEmptyPage() throws Exception {
        mockMvc.perform(get("/page/data"))
               .andExpect(status().isOk())
               .andExpect(view().name("data"));
    }

    @Test
    void getStockData_ValidSymbol_ReturnsDataPage() throws Exception {
        // Arrange
        String mockIntradayData = """
            {
                "Time Series (5min)": {
                    "2024-01-01 12:00:00": {
                        "1. open": "100.0",
                        "2. high": "101.0",
                        "3. low": "99.0",
                        "4. close": "100.5",
                        "5. volume": "1000"
                    }
                }
            }""";
        
        String mockMonthlyData = """
            {
                "Monthly Time Series": {
                    "2024-01-01": {
                        "1. open": "100.0",
                        "4. close": "101.0"
                    }
                }
            }""";

        when(alphaVantageService.getStockData(anyString(), anyString()))
            .thenReturn(mockIntradayData)
            .thenReturn(mockMonthlyData);

        // Act & Assert
        mockMvc.perform(get("/page/data").param("symbol", "IBM"))
               .andExpect(status().isOk())
               .andExpect(view().name("data"))
               .andExpect(model().attributeExists("stockDataMap"))
               .andExpect(model().attributeExists("analysis"));
    }

    @Test
    void getStockData_ErrorCase_ReturnsErrorMessage() throws Exception {
        // Arrange
        when(alphaVantageService.getStockData(anyString(), anyString()))
            .thenThrow(new RuntimeException("API Error"));

        // Act & Assert
        mockMvc.perform(get("/page/data").param("symbol", "INVALID"))
               .andExpect(status().isOk())
               .andExpect(view().name("data"))
               .andExpect(model().attributeExists("error"));
    }
}