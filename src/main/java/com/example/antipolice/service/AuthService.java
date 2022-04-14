package com.example.antipolice.service;

import com.example.antipolice.model.User;

public interface AuthService {
    User login(String username,String password);
}
