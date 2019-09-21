package com.example.demo.repositories;

import com.example.demo.models.Direction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DirectionRepository extends JpaRepository<Direction, Long>{
    // Working
    Optional<Direction> findById(Long id);
    void deleteById(Long id);
}
