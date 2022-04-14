package com.example.antipolice.model;


import com.example.antipolice.model.converters.LatLngConverter;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Entity
@Data
public class MapCoordinates {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long mapId;

    LocalDateTime timeSubmited;

    String formattedDateTime;

    @Convert(converter = LatLngConverter.class)
    LatLng latlng;

    @Enumerated(EnumType.STRING)
    MapCoordStatus status;

    @Enumerated(EnumType.STRING)
    PoliceState state;

    @ManyToOne
    User user;



    public MapCoordinates() {
    }

    public MapCoordinates(LatLng latlng, PoliceState state,User user) {
        this.latlng = latlng;
        this.state = state;
        this.status = MapCoordStatus.VALID;
        this.timeSubmited = LocalDateTime.now();
        this.formattedDateTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss dd/MM/yyyy"));
        this.user = user;
    }

    public MapCoordinates(LatLng latlng) {
        this.latlng = latlng;
    }
}
