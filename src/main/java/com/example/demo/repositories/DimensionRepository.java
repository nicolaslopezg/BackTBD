package com.example.demo.repositories;

import com.example.demo.models.Dimension;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DimensionRepository extends JpaRepository<Dimension, Long> {

    Dimension findDimensionById(Long id);
    Dimension findDimensionByName(String name);

}