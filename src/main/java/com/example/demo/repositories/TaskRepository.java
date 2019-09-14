package com.example.demo.Repositories;

import com.example.demo.Models.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long>{
    Task findTaskById(Long id);
    Task findTaskByType(String type);
    Task findTaskByState(Integer state);
}
