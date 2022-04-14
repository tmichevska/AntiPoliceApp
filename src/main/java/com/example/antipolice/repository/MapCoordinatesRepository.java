package com.example.antipolice.repository;

import com.example.antipolice.model.MapCoordStatus;
import com.example.antipolice.model.MapCoordinates;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MapCoordinatesRepository extends JpaRepository<MapCoordinates,Long> {

    List<MapCoordinates> findAllByStatus(MapCoordStatus status);

}
