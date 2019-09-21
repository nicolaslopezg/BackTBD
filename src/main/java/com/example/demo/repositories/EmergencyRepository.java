package com.example.demo.Repositories;

import com.example.demo.models.Emergency;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EmergencyRepository extends JpaRepository<Emergency, Long>{
    // Funcionando
    Optional<Emergency> findById(Long id);
    void deleteById(Long id);
}
