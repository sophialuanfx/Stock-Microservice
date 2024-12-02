package com.stock.microservice.project.controller;

import org.springframework.web.bind.annotation.*;
import com.stock.microservice.project.model.Subscription;
import java.util.List;

@RestController
@RequestMapping("/api/subscription")
public class SubscriptionController {
    // Define endpoints for adding, removing, and viewing subscriptions
    @PostMapping("/add")
    public String addSubscription(@RequestBody Subscription subscription) {
        // Logic to add subscription
        return "Subscription added successfully!";
    }

    @GetMapping("/user/{userId}")
    public List<Subscription> getUserSubscriptions(@PathVariable Long userId) {
        // Logic to get subscriptions for a user
        return null;
    }
}
