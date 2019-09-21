package com.example.demo.controllers;

import com.example.demo.models.Direction;
import com.example.demo.repositories.DirectionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/")
@CrossOrigin(origins = "*")

public class DirectionController {

    @Autowired
    DirectionRepository repository;

    @GetMapping("/directions")
    public List<Direction> getAll(){return repository.findAll(); }

    @GetMapping("/directions/{id}")
    Optional<Direction> getDirectionId(@PathVariable Long id) { return repository.findById(id); }

    @PostMapping("/directions")
    Direction insertDirection(@RequestBody Direction newDirection) { return repository.save(newDirection); }

    @PutMapping("/directions/{id}")
    public ResponseEntity<Object> updateDirection(@RequestBody Direction student, @PathVariable long id) {

        Optional<Direction> studentOptional = repository.findById(id);
        if (!studentOptional.isPresent())
            return ResponseEntity.notFound().build();
        student.setId(id);
        repository.save(student);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/directions/{id}")
    public void deleteDirection(@PathVariable Long id) { repository.deleteById(id); }

}
