package com.example.demo.controllers;

import com.example.demo.models.Task;
import com.example.demo.models.Emergency;
import com.example.demo.models.User;
import com.example.demo.repositories.EmergencyRepository;
import com.example.demo.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;

import java.util.*;

@RestController
@RequestMapping("/")
@CrossOrigin(origins = "*")

public class EmergencyController {

    @Autowired
    EmergencyRepository repository;
    @Autowired
    UserRepository userRepository;

    @GetMapping("/emergencies")
    public List<Emergency> getAll(){return repository.findAll(); }

    @GetMapping("/emergencies/{id}")
    Optional<Emergency> getEmergencyId(@PathVariable Long id) { return repository.findById(id); }

    @PostMapping("/emergencies/create")
    @ResponseBody
    public Emergency insertEmergency(@RequestBody Map<String, Object> jsonData) {
        List<HashMap<String, String>> result = new ArrayList<HashMap<String, String>>();
        HashMap<String, String> map = new HashMap<>();
        Long idUser = Long.parseLong(jsonData.get("user").toString());
        User user = userRepository.findUserByIdUser(idUser);
        if(user != null){
            map.put("status", "201");
            map.put("message", "Emergency added");
            result.add(map);
            return repository.save(new Emergency(jsonData.get("type").toString(),jsonData.get("description").toString(),
                    Integer.valueOf(jsonData.get("capacity").toString()),
                    Integer.valueOf(jsonData.get("status").toString()), user));
            }
        else {
            repository.save(new Emergency(jsonData.get("type").toString(),jsonData.get("description").toString(),
                    Integer.valueOf(jsonData.get("capacity").toString()),
                    Integer.valueOf(jsonData.get("status").toString()),user));
        }
        return repository.save(new Emergency(jsonData.get("type").toString(),jsonData.get("description").toString(),
                Integer.valueOf(jsonData.get("capacity").toString()),
                Integer.valueOf(jsonData.get("status").toString()), user));
    }

    @PutMapping("/emergencies/{id}")
    public ResponseEntity<Object> updateStudent(@RequestBody Emergency emergency, @PathVariable long id) {

        Optional<Emergency> studentOptional = repository.findById(id);
        if (!studentOptional.isPresent())
            return ResponseEntity.notFound().build();
        emergency.setIdEmergency(id);
        repository.save(emergency);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/emergencies/{id}")
    public void deleteEmergency(@PathVariable Long id) { repository.deleteById(id); }

}
