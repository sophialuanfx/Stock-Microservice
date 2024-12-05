package com.stock.microservice.project.controller;

import org.springframework.beans.factory.annotation.Autowired;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/page")
@Slf4j
public class PageController {
    
    @Autowired
    private ServiceDiscoveryController serviceDiscoveryController;
    
    @GetMapping("/login")
    public String login() {
        // Verify service availability before rendering
        return "login"; // 返回的字符串是templates文件夹中的filename.html
    }

    @GetMapping("/welcome")
    public String welcomepage() {
        return "welcome";
    }

    @GetMapping("/serviceregistry")
    public String serviceregistrypage() {
        return "serviceregistry";
    }



    @GetMapping("/home")
    public String homepage() {
        return "homepage";
    }

    @GetMapping("/register")
    public String registerpage() {
        log.info("Accessing register page at /register");
        log.debug("Attempting to return register.html template");
        return "register";
    }

    @GetMapping("/health")
    @ResponseBody
    public ResponseEntity<String> health() {
        log.info("Health check endpoint accessed");
        return ResponseEntity.ok("PageController is healthy");
    }
}
