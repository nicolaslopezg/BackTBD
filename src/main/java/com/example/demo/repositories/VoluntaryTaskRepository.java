package com.example.demo.repositories;

import com.example.demo.models.Task;
import com.example.demo.models.VoluntaryTask;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VoluntaryTaskRepository extends JpaRepository<VoluntaryTask, Long>{
    // Aún no funcionan.
    VoluntaryTask findVDById(Long id);
    List<VoluntaryTask> findVoluntaryTasksByTask(Task task);
}