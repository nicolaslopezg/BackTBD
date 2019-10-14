package com.example.demo.controllers;

import com.example.demo.models.Direction;
import com.example.demo.models.District;
import com.example.demo.models.Emergency;
import com.example.demo.models.Voluntary;
import com.example.demo.repositories.DirectionRepository;
import com.example.demo.repositories.DistrictRepository;
import com.example.demo.repositories.EmergencyRepository;
import com.example.demo.repositories.VoluntaryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;

import javax.validation.Valid;
import java.util.*;

// Servicio REST de Dirección.
@RestController
@CrossOrigin(origins = "*")

public class DirectionController {

    // Repositorios a utilizar.
    @Autowired
    DirectionRepository repository;
    @Autowired
    VoluntaryRepository voluntaryRepository;
    @Autowired
    EmergencyRepository emergencyRepository;
    @Autowired
    DistrictRepository districtRepository;

    // Servicios.
    @GetMapping("/directions")
    public List<Direction> getAll(){return repository.findAll(); }

    @GetMapping("/directions/{id}")
    Direction getDirectionId(@PathVariable Long id) { return repository.findDirectionById(id); }

    @PostMapping("/direction/create")
    @ResponseBody
    public Direction insertDirection(@RequestBody Map<String, Object> jsonData) {

        Long idVoluntary = Long.parseLong(jsonData.get("voluntary").toString());
        Long idEmergency = Long.parseLong(jsonData.get("emergency").toString());
        Long idDistrict = Long.parseLong(jsonData.get("district").toString());

        try {
            Voluntary voluntary = voluntaryRepository.findVoluntaryById(idVoluntary);
            try {
                Emergency emergency = emergencyRepository.findEmergencyById(idEmergency);
                try {
                    District district = districtRepository.findDistrictById(idDistrict);
                    // Una vez que se verifica que ninguna referencia sea nula
                    return repository.save(new Direction(jsonData.get("street").toString(),Integer.valueOf(jsonData.get("number").toString()),emergency,district,voluntary));
                }
                catch (NullPointerException e) {
                    System.out.println("District does not exist!!");
                }
            }
            catch (NullPointerException e ) {
                System.out.println("Emergency does not exist!!");
            }
        }
        catch (NullPointerException e) {
            System.out.println("Volunteer does not exist!!");
        }
        return new Direction();
    }

    @PutMapping("/directions/{id}")
    public ResponseEntity<Object> updateDirection(@RequestBody Direction student, @PathVariable long id) {

        Optional<Direction> directionOptional = repository.findById(id);
        if (!directionOptional.isPresent())
            return ResponseEntity.notFound().build();
        student.setId(id);
        repository.save(student);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/directions/{id}")
    public void deleteDirection(@PathVariable Long id) { repository.deleteById(id); }

}
