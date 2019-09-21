package com.example.demo.Repositories;

import com.example.demo.Models.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {
    Student findAlumnoById(Long id);
    Student findAlumnoByCarrera(String carrera);
    Student findAlumnoByRut(String rut);
}
