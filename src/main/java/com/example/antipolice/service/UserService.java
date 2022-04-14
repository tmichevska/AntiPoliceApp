package com.example.antipolice.service;

import com.example.antipolice.model.User;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {
    User register(String username,String password,int age);
}
