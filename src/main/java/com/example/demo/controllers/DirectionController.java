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

@RestController
@RequestMapping("/")
@CrossOrigin(origins = "*")

public class DirectionController {

    @Autowired
    DirectionRepository repository;
    @Autowired
    VoluntaryRepository voluntaryRepository;
    @Autowired
    EmergencyRepository emergencyRepository;
    @Autowired
    DistrictRepository districtRepository;

    @GetMapping("/directions")
    public List<Direction> getAll(){return repository.findAll(); }

    @GetMapping("/directions/{id}")
    Optional<Direction> getDirectionId(@PathVariable Long id) { return repository.findById(id); }

    @PostMapping("/direction/create")
    @ResponseBody
    public List<HashMap<String, String>> insertDirection(@RequestBody Map<String, Object> jsonData) {
        List<HashMap<String, String>> result = new ArrayList<HashMap<String, String>>();
        HashMap<String, String> map = new HashMap<>();
        Long idVoluntary = Long.parseLong(jsonData.get("voluntary").toString());
        Long idEmergency = Long.parseLong(jsonData.get("emergency").toString());
        Long idDistrict = Long.parseLong(jsonData.get("district").toString());
        Voluntary voluntary = voluntaryRepository.findVoluntaryByIdVoluntary(idVoluntary);
        if(voluntary != null){
            Emergency emergency = emergencyRepository.findEmergencyByIdEmergency(idEmergency);
            if(emergency != null){
                District district = districtRepository.findDistrictById(idDistrict);
                if(district != null){
                    repository.save(new Direction(jsonData.get("street").toString(),
                            Integer.valueOf(jsonData.get("number").toString()),
                            emergency,district,voluntary));
                    map.put("status", "201");
                    map.put("message", "Direction added");
                    result.add(map);
                    return result;
                } else {
                    map.put("status", "401");
                    map.put("message", "District does not exist!.");
                    result.add(map);
                    return result;
                }

            } else {
                map.put("status", "401");
                map.put("message", "Emergency does not exist!.");
                result.add(map);
                return result;
            }
        } else {
            map.put("status", "401");
            map.put("message", "Voluntary does not exist!.");
            result.add(map);
            return result;
        }
    }

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
