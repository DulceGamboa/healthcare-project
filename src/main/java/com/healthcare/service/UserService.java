package com.healthcare.service;

import com.healthcare.model.User;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {

    private final List<User> users = new ArrayList<>();

    public void addUser(User user) {
        users.add(user);
        System.out.println("User added: " + user);
    }

    public List<User> getAllUsers() {
        return users;
    }
}