package com.stock.microservice.project.controller;

import lombok.extern.slf4j.Slf4j;
import java.util.Map;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/service-discovery")
@Slf4j
public class ServiceDiscoveryController {
    
    @GetMapping("/")
    public ResponseEntity<String> rootHealthCheck() {
        return ResponseEntity.ok("Service Discovery Root is healthy");
    }

    @GetMapping("/health")
    public ResponseEntity<String> healthCheck() {
        log.info("Health check endpoint accessed");
        return ResponseEntity.ok("Service Discovery is healthy");
    }

    @GetMapping("/services")
    public ResponseEntity<?> getAvailableServices() {
        var services = Map.of(
            "register", "user-registry:8080",
            "login", "stock-microservice:8080",
            "data", "stock-performance:8080"
        );
        return ResponseEntity.ok(services);
    }

    @GetMapping("/route")
    public ResponseEntity<String> getServiceRoute(String path) {
        String service = switch (path) {
            case "/page/register" -> "user-registry:8080";
            case "/page/login" -> "stock-microservice:8080";
            case "/page/data" -> "stock-performance:8080";
            default -> null;
        };
        
        if (service == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(service);
    }
}
