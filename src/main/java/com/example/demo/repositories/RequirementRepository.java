package com.example.demo.Repositories;

import com.example.demo.Models.Requirement;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RequirementRepository extends JpaRepository<Requirement, Long> {

    Requirement findRequirementByIdRequirement(Long idRole);
    Requirement findRequirementByTipo(int tipo);
}
