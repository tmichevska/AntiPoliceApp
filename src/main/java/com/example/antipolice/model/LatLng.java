package com.example.antipolice.model;

import lombok.Data;

import java.io.Serializable;

@Data
public class LatLng implements Serializable {
    public String lat;
    public String lng;

    public LatLng() {
    }

    public LatLng(String lat, String lng) {
        this.lat = lat;
        this.lng = lng;
    }
}
