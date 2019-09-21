package com.example.demo.Repositories;

import com.example.demo.models.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long>{
    // Funcionando
    Optional<Task> findById(Long id);
    void deleteById(Long id);
}
