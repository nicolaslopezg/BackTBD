package com.example.demo.controllers;

import com.example.demo.models.*;
import com.example.demo.repositories.*;
import com.example.demo.models.Emergency;
import com.example.demo.models.Task;
import com.example.demo.models.User;
import com.example.demo.models.VoluntaryTask;
import com.example.demo.repositories.EmergencyRepository;
import com.example.demo.repositories.TaskRepository;
import com.example.demo.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import com.example.demo.repositories.VoluntaryTaskRepository;

import javax.validation.Valid;
import java.util.*;

// Servicio REST de tarea.
@RestController
@CrossOrigin(origins = "*")

public class TaskController {

    // Repositorios utilizados.
    @Autowired
    TaskRepository repository;
    @Autowired
    EmergencyRepository emergencyRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    VoluntaryTaskRepository voluntaryTaskRepository;
    @Autowired
    VoluntaryRepository voluntaryRepository;

    // Servicios
    @GetMapping("/tasks")
    public List<Task> getAll(){return repository.findAll(); }

    @GetMapping("/tasks/{id}")
    Task getTaskId(@PathVariable Long id) { return repository.findTaskById(id); }

    @GetMapping("/taskByEmergency/{id}")
    @ResponseBody
    public List<Task> getTaskByEmergency(@PathVariable Long id) {
        Emergency emergency = emergencyRepository.findEmergencyById(id);
        return repository.findTasksByEmergency(emergency);
    }

    @PostMapping("/tasks/create")
    @ResponseBody
    public Task insertTask(@RequestBody Map<String, Object> jsonData) {
        List<HashMap<String, String>> result = new ArrayList<HashMap<String, String>>();
        HashMap<String, String> map = new HashMap<>();
        Long idUser = Long.parseLong(jsonData.get("user").toString());
        Long idEmergency = Long.parseLong(jsonData.get("emergency").toString());
        try {
            User user = userRepository.findUserById(idUser);
            try {
                Emergency emergency = emergencyRepository.findEmergencyById(idEmergency);
                return repository.save(new Task(jsonData.get("type").toString(), jsonData.get("description").toString(), Integer.parseInt(jsonData.get("capacity").toString()), Integer.parseInt(jsonData.get("status").toString()), emergency,user));
            }
            catch (NullPointerException e) {
                System.out.println("Emergency does not exist!!");
            }
        }
        catch (NullPointerException e) {
            System.out.println("User does not exist!!");
        }

        return new Task();
    }

    @PutMapping("/tasks/{id}")
    public ResponseEntity<Object> updateTask(@RequestBody Task task, @PathVariable long id) {

        Optional<Task> taskOptional = repository.findById(id);
        if (!taskOptional.isPresent())
            return ResponseEntity.notFound().build();
        task.setId(id);
        repository.save(task);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/tasks/{id}")
    public void deleteTask(@PathVariable Long id) {
        try {
            Task task = repository.findTaskById(id);
            try {
                VoluntaryTask relation = voluntaryTaskRepository.findVTByTask(task);
                voluntaryTaskRepository.deleteById(relation.getId());
                repository.deleteById(id);
            }
            catch (NullPointerException e) {
                System.out.println("Relation between task and voluntary does not exist!!");
                repository.deleteById(id);
            }
        }
        catch (NullPointerException e) {
            System.out.println("Task does not exist!!");
        }
    }


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
            if (task.getStatus()!=1){
                map.put("status", "404");
                map.put("message", "Task does not finish!.");
                map.put("item", "");
                result.add(map);
                return result;
            }
            task.setStatus(2);
            repository.save(task);
            map.put("status", "200");
            map.put("message", "OK");
            map.put("item", task.getDescription());
            result.add(map);
            return result;
        }
    }

}
