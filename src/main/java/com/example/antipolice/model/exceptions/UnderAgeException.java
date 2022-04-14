package com.example.antipolice.model.exceptions;

public class UnderAgeException extends RuntimeException{
    public UnderAgeException(int age) {
        super("You are " + age + ". Under age!");
    }
}
