package com.example.antipolice.model.exceptions;

public class UsernameAlreadyExistsException extends RuntimeException{
    public UsernameAlreadyExistsException(String message) {
        super("User with username "+ message+" already exist. Please chose another username!");
    }
}
