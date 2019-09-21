package com.example.demo.repositories;

import com.example.demo.models.VoluntaryDimension;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VoluntaryDimensionRepository extends JpaRepository<VoluntaryDimension, Long>{
    // Aún no funcionan.
    VoluntaryDimension findVDById(Long id);
}