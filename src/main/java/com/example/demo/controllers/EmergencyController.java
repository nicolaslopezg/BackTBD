package com.example.demo.controllers;

import com.example.demo.models.Task;
import com.example.demo.models.Emergency;
import com.example.demo.repositories.EmergencyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/")
@CrossOrigin(origins = "*")

public class EmergencyController {

    @Autowired
    EmergencyRepository repository;

    @GetMapping("/emergencies")
    public List<Emergency> getAll(){return repository.findAll(); }

    @GetMapping("/emergencies/{id}")
    Optional<Emergency> getEmergencyId(@PathVariable Long id) { return repository.findById(id); }

    @PostMapping("/emergencies")
    Emergency insertEmergency(@RequestBody Emergency newEmergency) { return repository.save(newEmergency); }

    @PutMapping("/emergencies/{id}")
    public ResponseEntity<Object> updateStudent(@RequestBody Emergency emergency, @PathVariable long id) {

        Optional<Emergency> studentOptional = repository.findById(id);
        if (!studentOptional.isPresent())
            return ResponseEntity.notFound().build();
        emergency.setId(id);
        repository.save(emergency);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/emergencies/{id}")
    public void deleteEmergency(@PathVariable Long id) { repository.deleteById(id); }

}
