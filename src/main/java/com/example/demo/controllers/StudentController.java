package com.example.demo.controllers;

import com.example.demo.models.Student;
import com.example.demo.repositories.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/")
@CrossOrigin(origins = "*")
public class StudentController {

    @Autowired
    StudentRepository repository;

    @GetMapping("/students")
    public List<Student> getAll(){return repository.findAll();}

    @PostMapping("/students")
    Student insertAlumno(@RequestBody Student newAlumno){
        return repository.save(newAlumno);
    }

    @GetMapping("/students/{id}")
    Student getAlumnoId(@PathVariable Long id){
        return repository.findAlumnoById(id);
    }
}