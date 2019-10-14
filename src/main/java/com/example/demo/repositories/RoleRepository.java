package com.example.demo.repositories;

import com.example.demo.models.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {

    Role findRoleById(Long id);
    Role findRoleByType(int type);

}
