package com.healthcare.controller;

import com.healthcare.model.User;
import com.healthcare.service.UserService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    // POST - create user
    @PostMapping
    public String createUser(@RequestBody User user) {
        userService.addUser(user);
        return "User added successfully";
    }

    // GET - list users
    @GetMapping
    public List<User> getUsers() {
        return userService.getAllUsers();
    }
}