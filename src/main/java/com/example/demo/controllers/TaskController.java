package com.example.demo.Controllers;

import com.example.demo.Models.Task;
import com.example.demo.Repositories.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/")
@CrossOrigin(origins = "*")

public class TaskController {

    @Autowired
    TaskRepository repository;

    @GetMapping("/tasks")
    public List<Task> getAll(){return repository.findAll(); }

    @GetMapping("/tasks/{id}")
    Optional<Task> getTaskId(@PathVariable Long id) { return repository.findById(id); }

    @PostMapping("/tasks")
    Task insertTask(@RequestBody Task newTask) { return repository.save(newTask); }

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

}
