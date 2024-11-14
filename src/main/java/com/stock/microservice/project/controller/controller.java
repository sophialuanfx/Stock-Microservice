package com.stock.microservice.project.controller;
import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.stock.microservice.project.model.User;
import com.stock.microservice.project.model.UserRegistry;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/users")

public class Controller {

    @Autowired
    private UserRegistry userRegistry;

    @PostMapping("/register")
    public String registerUser(@RequestParam String userName, @RequestParam String company) {
        User newUser = new User(0, userName, company);
        userRegistry.addUser(newUser);
        try {
            userRegistry.saveUsersToJson();
            return "User registered successfully!";
        } catch (IOException e) {
            e.printStackTrace();
            return "Error saving user data.";
        }
    }

    @GetMapping("/{id}")
    public User getUserById(@PathVariable int id) {
        User user = userRegistry.getUserById(id);
        if (user != null) {
            return user;
        } else {
            return null;
        }
    }
}
