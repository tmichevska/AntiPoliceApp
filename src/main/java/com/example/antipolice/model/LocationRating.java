package com.example.antipolice.model;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
public class LocationRating {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long id;

    @OneToOne
    User user;

    @OneToOne
    MapCoordinates mapCoordinates;

    Integer likes;

    Integer dislikes;

    @Enumerated
    RatingStatus status;

    public LocationRating()
    {

    }

    public LocationRating(Long id, User user, MapCoordinates mapCoordinates) {
        this.id = id;
        this.user = user;
        this.mapCoordinates = mapCoordinates;
        this.likes=0;
        this.dislikes=0;
        this.status = RatingStatus.REAL;
    }
}
