package com.example.antipolice.model.exceptions;

public class InvalidArgumentsException extends RuntimeException{
    public InvalidArgumentsException() {
        super("Please fill out all fields");
    }
}
