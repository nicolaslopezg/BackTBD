package com.example.demo.repositories;

import com.example.demo.models.Emergency;
import com.example.demo.models.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long>{
    // Funcionando
    Optional<Task> findById(Long id);
    List<Task> findTasksByEmergency(Emergency emergency);
    Task findTasksById(Long id);
    Task findTaskById(Long id);
    void deleteById(Long id);
}
