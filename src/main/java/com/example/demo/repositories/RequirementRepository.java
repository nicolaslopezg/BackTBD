package com.example.demo.repositories;

import com.example.demo.models.Requirement;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RequirementRepository extends JpaRepository<Requirement, Long> {

    Requirement findRequirementByIdRequirement(Long idRole);
    Requirement findRequirementByTipo(int tipo);
}
