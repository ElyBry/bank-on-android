package com.example.bank2.service;

import com.example.bank2.dto.LoginRequest;
import com.example.bank2.dto.RegisterRequest;
import com.example.bank2.entity.User;

public interface UserService {
    User register(RegisterRequest request);
    User login(LoginRequest request);
    User getUserByLogin(String login);
    User getUserById(Integer id);
    boolean existsByLogin(String login);
    boolean existsByPassport(String passport);
}

