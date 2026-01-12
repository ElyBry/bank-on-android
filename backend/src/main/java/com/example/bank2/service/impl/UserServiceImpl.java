package com.example.bank2.service.impl;

import com.example.bank2.dto.LoginRequest;
import com.example.bank2.dto.RegisterRequest;
import com.example.bank2.entity.User;
import com.example.bank2.repository.UserRepository;
import com.example.bank2.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public User register(RegisterRequest request) {
        // TODO: Implement registration logic
        return null;
    }

    @Override
    public User login(LoginRequest request) {
        // TODO: Implement login logic
        return null;
    }

    @Override
    public User getUserByLogin(String login) {
        // TODO: Implement get user by login
        return null;
    }

    @Override
    public User getUserById(Integer id) {
        // TODO: Implement get user by id
        return null;
    }

    @Override
    public boolean existsByLogin(String login) {
        // TODO: Implement check if user exists by login
        return false;
    }

    @Override
    public boolean existsByPassport(String passport) {
        // TODO: Implement check if user exists by passport
        return false;
    }
}

