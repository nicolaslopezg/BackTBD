package com.example.demo.Repositories;

import com.example.demo.Models.VoluntaryDimension;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VoluntaryDimensionRepository extends JpaRepository<VoluntaryDimension, Long>{
    // Aún no funcionan.
    VoluntaryDimension findVDById(Long id);
}