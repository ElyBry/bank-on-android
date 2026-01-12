package com.example.bank2.controller;

import com.example.bank2.dto.ApiResponse;
import com.example.bank2.entity.User;
import com.example.bank2.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<User>> getUserById(@PathVariable Integer id) {
        // TODO: Implement get user by id endpoint
        return null;
    }

    @GetMapping("/login/{login}")
    public ResponseEntity<ApiResponse<User>> getUserByLogin(@PathVariable String login) {
        // TODO: Implement get user by login endpoint
        return null;
    }
}

