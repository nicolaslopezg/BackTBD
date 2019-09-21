package com.example.demo.repositories;

import com.example.demo.models.VoluntaryTask;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VoluntaryTaskRepository extends JpaRepository<VoluntaryTask, Long>{
    // Aún no funcionan.
    VoluntaryTask findVDById(Long id);
}