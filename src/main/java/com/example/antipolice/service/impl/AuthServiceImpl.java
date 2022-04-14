package com.example.antipolice.service.impl;

import com.example.antipolice.model.User;
import com.example.antipolice.model.exceptions.InvalidArgumentsException;
import com.example.antipolice.model.exceptions.InvalidUserCredentialsException;
import com.example.antipolice.repository.UserRepository;
import com.example.antipolice.service.AuthService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;

    public AuthServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User login(String username, String password) {
        if (username==null || username.isEmpty() || password==null || password.isEmpty()) {
            throw new InvalidArgumentsException();
        }

        return userRepository.findByUsername(username)
                .orElseThrow(InvalidUserCredentialsException::new);
    }
}
