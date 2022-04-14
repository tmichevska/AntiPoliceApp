package com.example.antipolice.service.impl;

import com.example.antipolice.model.User;
import com.example.antipolice.model.exceptions.InvalidArgumentsException;
import com.example.antipolice.model.exceptions.UnderAgeException;
import com.example.antipolice.model.exceptions.UsernameAlreadyExistsException;
import com.example.antipolice.repository.UserRepository;
import com.example.antipolice.service.UserService;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    private boolean isNullOrEmpty(String value)
    {
        return value==null || value.isEmpty();
    }

    public User register(String username, String password, int age){
        if(isNullOrEmpty(username) || isNullOrEmpty(password) || age <= 0)
            throw new InvalidArgumentsException();
        if(age<18)
            throw new UnderAgeException(age);
        if(this.userRepository.findByUsername(username).isPresent())
            throw new UsernameAlreadyExistsException(username);
        return this.userRepository.save(new User(username,passwordEncoder.encode(password),age));
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException(username));
    }
}
