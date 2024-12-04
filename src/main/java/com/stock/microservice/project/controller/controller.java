package com.stock.microservice.project.controller;
import java.io.File;
import java.io.IOException;
import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.stock.microservice.project.model.User;
import com.stock.microservice.project.model.userRegistry;

import com.fasterxml.jackson.core.type.TypeReference;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/users")
public class controller {

    @Autowired
    private userRegistry userRegistry;

    @PostMapping("/register")
    public String registerUser(@RequestParam String userName, @RequestParam String company) {
        if (userName == null || userName.trim().isEmpty() || company == null || company.trim().isEmpty()) {
            return "Username and company are required.";
        }
        User newUser = new User(0, userName, company);
        userRegistry.addUser(newUser);
        try {
            userRegistry.saveUsersToJson();
            return "User registered successfully!";
        } catch (IOException e) {
            log.error("Failed to save user data", e);
            return "Error saving user data.";
        }
    }

    @PostMapping("/login")
    public ResponseEntity<String> loginUser(@RequestParam String userName, @RequestParam String company) {
        ObjectMapper mapper = new ObjectMapper();
        String FILE_PATH = "/tmp/userdata/users.json";
        File file = new File(FILE_PATH);

        if (!file.exists()) {
            return ResponseEntity.badRequest().body("User database not found.");
        }

        try {
            List<User> users = mapper.readValue(file, new TypeReference<List<User>>() {});
            System.out.println("Debug: Users loaded - " + users.size() + " users found.");

            for (User user : users) {
                System.out.println("Debug: Checking user - UserName: " + user.getUserName() + ", Company: " + user.getCompany());
                if (user.getUserName().equalsIgnoreCase(userName) && user.getCompany().equalsIgnoreCase(company)) {
                    System.out.println("Debug: User match found.");
                    return ResponseEntity.ok(userName + " login successful");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().body("Error processing the user file.");
        }
        return ResponseEntity.badRequest().body("Login failed");
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
