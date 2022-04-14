package com.example.antipolice.model.exceptions;

public class LatLngNotFoundException extends RuntimeException{
    public LatLngNotFoundException() {
        super("LatLngNotFound");
    }
}
