package com.example.demo.controllers;

import com.example.demo.models.Direction;
import com.example.demo.models.Task;
import com.example.demo.models.Emergency;
import com.example.demo.models.User;
import com.example.demo.repositories.DirectionRepository;
import com.example.demo.repositories.EmergencyRepository;
import com.example.demo.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;

import java.util.*;

// Servicio REST de Emergencia.
@RestController
@CrossOrigin(origins = "*")

public class EmergencyController {

    // Repositorios a utilizar.
    @Autowired
    EmergencyRepository repository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    DirectionRepository directionRepository;

    // Servicios.
    @GetMapping("/emergencies")
    public List<Emergency> getAll(){return repository.findAll(); }

    @GetMapping("/emergencies/{id}")
    Optional<Emergency> getEmergencyId(@PathVariable Long id) { return repository.findById(id); }

    @PostMapping("/emergencies/create")
    @ResponseBody
    public Emergency insertEmergency(@RequestBody Map<String, Object> jsonData) {

        Long idUser = Long.parseLong(jsonData.get("user").toString());

        try {
            User user = userRepository.findUserById(idUser);
                    // Una vez que se verifica que ninguna referencia sea nula
            return repository.save(new Emergency(jsonData.get("type").toString(),jsonData.get("description").toString(),Integer.valueOf(jsonData.get("capacity").toString()),Integer.valueOf(jsonData.get("status").toString()),user));
        }
        catch (NullPointerException e) {
            System.out.println("User does not exist!!");
        }
        return new Emergency();
    }

    @PostMapping("/emergencies/ncreate")
    @ResponseBody
    public Emergency nInsertEmergency(@RequestBody Map<String, Object> jsonData) {

        Long idUser = Long.parseLong(jsonData.get("user").toString());

        try {
            User user = userRepository.findUserById(idUser);
            // Una vez que se verifica que ninguna referencia sea nula
            return repository.save(new Emergency(jsonData.get("type").toString(),
                                                jsonData.get("description").toString(),
                                                Integer.valueOf(jsonData.get("capacity").toString()),
                                                Integer.valueOf(jsonData.get("status").toString()),
                                                user,
                                                jsonData.get("latitude").toString(),
                                                jsonData.get("longitude").toString()));
        }
        catch (NullPointerException e) {
            System.out.println("User does not exist!!");
        }
        return new Emergency();
    }

    @PutMapping("/emergencies/{id}")
    public ResponseEntity<Object> updateEmergency(@RequestBody Emergency emergency, @PathVariable long id) {

        Optional<Emergency> emergencyOptional = repository.findById(id);
        if (!emergencyOptional.isPresent())
            return ResponseEntity.notFound().build();
        emergency.setIdEmergency(id);
        repository.save(emergency);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/emergencies/{id}")
    public void deleteEmergency(@PathVariable Long id) {
        try {
            Emergency emergency = repository.findEmergencyById(id);
            try {
                Direction direction = directionRepository.findDirectionByEmergency(emergency);
                directionRepository.deleteById(direction.getId());
                repository.deleteById(id);
            }
            catch (NullPointerException e) {
                System.out.println("District does not exist!!");
                repository.deleteById(id);
            }
        }
        catch (NullPointerException e) {
            System.out.println("Emergency does not exist!!");
        }
    }

}
