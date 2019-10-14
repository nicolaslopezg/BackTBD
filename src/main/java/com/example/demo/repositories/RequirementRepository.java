package com.example.demo.repositories;

import com.example.demo.models.Requirement;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RequirementRepository extends JpaRepository<Requirement, Long> {

    Requirement findRequirementById(Long id);
    Requirement findRequirementByType(int type);
}
