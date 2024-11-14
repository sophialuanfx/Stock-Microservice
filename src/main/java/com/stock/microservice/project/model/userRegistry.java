package com.stock.microservice.project.model;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class userRegistry {

    private List<User> users = new ArrayList<>();
    private static final String FILE_PATH = "users.json";

    // Add a user to the registry
    public void addUser(User user) {
        users.add(user);
    }

    // Save all users to a JSON file
    public void saveUsersToJson() throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.writerWithDefaultPrettyPrinter().writeValue(new File(FILE_PATH), users);
    }

    // Load users from a JSON file into the registry
    public void loadUsersFromJson() throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        File file = new File(FILE_PATH);
        if (file.exists()) {
            users = mapper.readValue(file, new TypeReference<List<User>>() {});
        }
    }

    // Retrieve a user by userName
    public User getUserByUserName(String userName) {
        for (User user : users) {
            if (user.getUserName().equalsIgnoreCase(userName)) {
                return user;
            }
        }
        return null; // Return null if the user is not found
    }

    public List<User> getUsers() {
        return users;
    }
}

