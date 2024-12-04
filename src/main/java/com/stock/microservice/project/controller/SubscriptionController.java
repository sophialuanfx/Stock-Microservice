package com.stock.microservice.project.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import com.stock.microservice.project.model.Subscription;
import com.stock.microservice.project.service.SubscriptionService;
import java.util.List;

@Controller
@RequestMapping("/subscriptions")
public class SubscriptionController {
    @Autowired
    private SubscriptionService subscriptionService;

    @GetMapping({"/", "/manage"})
    public String manageSubscriptions() {
        return "redirect:/subscriptions/manage-page";
    }

    @GetMapping("/manage-page")
    public String manageSubscriptionsPage() {
        return "manage-subscriptions";
    }

    @GetMapping
    public String viewSubscriptions(Model model) {
        List<Subscription> subscriptions = subscriptionService.getAllSubscriptions();
        model.addAttribute("subscriptions", subscriptions);
        return "subscriptions";
    }

    @PostMapping("/api/add")
    @ResponseBody
    public String addSubscription(@RequestBody Subscription subscription) {
        subscriptionService.addSubscription(subscription);
        return "Subscription added successfully!";
    }

    @DeleteMapping("/api/remove/{stockCode}")
    @ResponseBody
    public String removeSubscription(@PathVariable String stockCode) {
        subscriptionService.removeSubscription(stockCode);
        return "Subscription removed successfully!";
    }

    @GetMapping("/api/user/{userId}")
    @ResponseBody
    public List<Subscription> getUserSubscriptions(@PathVariable Long userId) {
        return subscriptionService.getSubscriptionsByUser(userId);
    }
}
