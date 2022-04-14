package com.example.antipolice.model;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
public class StatusAccurabillity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long Id;

    boolean status;

    @OneToOne
    User user;

    @ManyToOne
    MapCoordinates mapCoordinates;


}

