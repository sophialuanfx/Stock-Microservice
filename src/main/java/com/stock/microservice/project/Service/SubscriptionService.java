package com.stock.microservice.project.service;

import com.stock.microservice.project.model.Subscription;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class SubscriptionService {
    private List<Subscription> subscriptions = new ArrayList<>();

    public void addSubscription(Subscription subscription) {
        subscriptions.add(subscription);
    }

    public void removeSubscription(String stockCode) {
        subscriptions.removeIf(subscription -> 
            subscription.getStockCode().equals(stockCode));
    }

    public List<Subscription> getAllSubscriptions() {
        return subscriptions;
    }

    public List<Subscription> getSubscriptionsByUser(Long userId) {
        return subscriptions.stream()
            .filter(subscription -> subscription.getUserId().equals(userId))
            .collect(Collectors.toList());
    }
}
