package com.stock.microservice.project.model;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Component
public class UserRegistry {

    private List<User> users = new ArrayList<>();
    private static final String FILE_PATH = "users.json";
    private int nextId = 1;

    public UserRegistry() {
        try {
            loadUsersFromJson(); // Load existing users on startup
            if (!users.isEmpty()) {
                // Set nextId to the highest id + 1
                nextId = users.stream().mapToInt(User::getId).max().orElse(0) + 1;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void addUser(User user) {
        user.setId(nextId++); // Assign unique id and increment nextId
        users.add(user);
    }

    public void saveUsersToJson() throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.writerWithDefaultPrettyPrinter().writeValue(new File(FILE_PATH), users);
    }

    public void loadUsersFromJson() throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        File file = new File(FILE_PATH);
        if (file.exists()) {
            users = mapper.readValue(file, new TypeReference<List<User>>() {});
        }
    }

    public User getUserById(int id) {
        for (User user : users) {
            if (user.getId() == id) {
                return user;
            }
        }
        return null; // Return null if user is not found
    }
}

