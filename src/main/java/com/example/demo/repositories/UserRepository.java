package com.example.demo.repositories;

import com.example.demo.models.Role;
import com.example.demo.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {

    User findUserById(Long idUser);
    User findUserByRut(int rut);
    List<User> findUserByRole(Role role);

}
