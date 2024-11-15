package com.stock.microservice.project.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.stock.microservice.project.service.AlphaVantageService;
import java.util.Map;


@Controller
@RequestMapping("/page")
public class PageController {

    private final AlphaVantageService stockService;

    @Autowired
    public PageController(AlphaVantageService stockService) {
        this.stockService = stockService;
    }

    @GetMapping("/login")
    public String loginpage() {
        return "loginpage"; // 返回的字符串是templates文件夹中的filename.html
    }


    @GetMapping("/home")
    public String homepage() {
        return "homepage";
    }

    @GetMapping("/register")
    public String registerpage() {
        return "register";
    }

    @GetMapping("/data")
    public String getStockData(@RequestParam(required = false) String symbol, Model model) {
        model.addAttribute("symbol", symbol);
        
        if (symbol != null && !symbol.isEmpty()) {
            try {
                String stockData = stockService.getStockData(symbol);
                model.addAttribute("stockData", stockData);
            } catch (Exception e) {
                e.printStackTrace();
                model.addAttribute("error", "Error fetching stock data: " + e.getMessage());
            }
        }
        return "data";
    }
}