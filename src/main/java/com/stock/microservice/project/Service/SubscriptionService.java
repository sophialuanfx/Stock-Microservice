package main.java.com.stock.microservice.project.service;

import com.stock.microservice.project.model.Subscription;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;

@Service
public class SubscriptionService {
    private List<Subscription> subscriptions = new ArrayList<>();

    public void addSubscription(Subscription subscription) {
        subscriptions.add(subscription);
    }

    public List<Subscription> getSubscriptionsByUser(Long userId) {
        List<Subscription> userSubscriptions = new ArrayList<>();
        for (Subscription subscription : subscriptions) {
            if (subscription.getUserID().equals(userId)) { // Correct method name here
                userSubscriptions.add(subscription);
            }
        }
        return userSubscriptions;
    }
}
