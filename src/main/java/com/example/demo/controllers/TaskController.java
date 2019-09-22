package com.example.demo.controllers;

import com.example.demo.models.Emergency;
import com.example.demo.models.Task;
import com.example.demo.models.User;
import com.example.demo.repositories.EmergencyRepository;
import com.example.demo.repositories.TaskRepository;
import com.example.demo.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;

import javax.validation.Valid;
import java.util.*;

@RestController
@RequestMapping("/")
@CrossOrigin(origins = "*")

public class TaskController {

    @Autowired
    TaskRepository repository;
    @Autowired
    EmergencyRepository emergencyRepository;
    @Autowired
    UserRepository userRepository;

    @GetMapping("/tasks")
    public List<Task> getAll(){return repository.findAll(); }

    @GetMapping("/tasks/{id}")
    Optional<Task> getTaskId(@PathVariable Long id) { return repository.findById(id); }

    @GetMapping("/taskByEmergency/{id}")
    @ResponseBody
    public List<Task> getTaskByEmergency(@PathVariable Long id) {
        Emergency emergency = emergencyRepository.findEmergencyByIdEmergency(id);
        return repository.findTasksByEmergency(emergency);
    }

    @PostMapping("/tasks/create")
    @ResponseBody
    public List<HashMap<String, String>> insertTask(@RequestBody Map<String, Object> jsonData) {
        List<HashMap<String, String>> result = new ArrayList<HashMap<String, String>>();
        HashMap<String, String> map = new HashMap<>();
        Long idUser = Long.parseLong(jsonData.get("user").toString());
        Long idEmergency = Long.parseLong(jsonData.get("emergency").toString());
        User user = userRepository.findUserByIdUser(idUser);
        Emergency emergency;
        if(user != null){
            emergency = emergencyRepository.findEmergencyByIdEmergency(idEmergency);
            if(emergency != null){
                repository.save(new Task(jsonData.get("type").toString(),
                        jsonData.get("description").toString(),
                        Integer.parseInt(jsonData.get("capacity").toString()),
                        Integer.parseInt(jsonData.get("state").toString()),
                        emergency,user));
                map.put("status", "201");
                map.put("message", "Task added");
                result.add(map);
                return result;
            } else {
                map.put("status", "401");
                map.put("message", "Emergency does not exist!.");
                result.add(map);
                return result;
            }
        } else {
            map.put("status", "401");
            map.put("message", "User does not exist!.");
            result.add(map);
            return result;
        }
    }

    // Falta que mande error si no se encuentra.
    // Falta que mande el mensaje de exito.
    @PutMapping("/tasks/{id}")
    public ResponseEntity<Object> updateTask(@RequestBody Task task, @PathVariable long id) {

        Optional<Task> taskOptional = repository.findById(id);
        if (!taskOptional.isPresent())
            return ResponseEntity.notFound().build();
        task.setId(id);
        repository.save(task);
        return ResponseEntity.noContent().build();
    }

    // Falta que mande el mensaje de exito.
    @DeleteMapping("/tasks/{id}")
    public void deleteTask(@PathVariable Long id) { repository.deleteById(id); }

    @PostMapping("/tasks/end")
    @ResponseBody
    public List<HashMap<String, String>> endTask(@RequestBody Map<String, Object> jsonData) {
        List<HashMap<String, String>> result = new ArrayList<HashMap<String, String>>();
        HashMap<String, String> map = new HashMap<>();
        Long idTask = Long.parseLong(jsonData.get("idTask").toString());
        Task task = repository.findTaskById(idTask);
        if(task == null) {
            map.put("status", "404");
            map.put("message", "Task does not exist!.");
            map.put("item", "");
            result.add(map);
            return result;
        }
        else {
            if (task.getState()!=1){
                map.put("status", "404");
                map.put("message", "Task does not finish!.");
                map.put("item", "");
                result.add(map);
                return result;
            }
            task.setState(2);
            repository.save(task);
            map.put("status", "200");
            map.put("message", "OK");
            map.put("item", task.getDescription());
            result.add(map);
            return result;
        }
    }

}
