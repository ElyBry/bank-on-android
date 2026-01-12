package com.example.bank2.controller;

import com.example.bank2.dto.ApiResponse;
import com.example.bank2.dto.LoginRequest;
import com.example.bank2.dto.RegisterRequest;
import com.example.bank2.entity.User;
import com.example.bank2.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public ResponseEntity<ApiResponse<User>> register(@RequestBody RegisterRequest request) {
        // TODO: Implement registration endpoint
        return null;
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<User>> login(@RequestBody LoginRequest request) {
        // TODO: Implement login endpoint
        return null;
    }
}

