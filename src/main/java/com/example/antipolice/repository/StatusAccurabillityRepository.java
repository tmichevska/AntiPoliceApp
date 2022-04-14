package com.example.antipolice.repository;

import com.example.antipolice.model.StatusAccurabillity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StatusAccurabillityRepository extends JpaRepository<StatusAccurabillity,Long> {
}
