package com.example.demo.Controllers;

import com.example.demo.Models.Task;
import com.example.demo.Repositories.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/")
@CrossOrigin(origins = "*")

public class TaskController {

    @Autowired
    TaskRepository repository;

    @GetMapping("/tasks")
    public List<Task> getAll(){return repository.findAll(); }

    @PostMapping("/tasks")
    Task insertTask(@RequestBody Task newTask){ return repository.save(newTask); }

    @GetMapping("/task_id/{id}")
    Task getTaskId(@PathVariable Long id){
        return repository.findTaskById(id);
    }

    @GetMapping("/task/{type}")
    Task getTaskType(@PathVariable String type){
        return repository.findTaskByType(type);
    }

    @GetMapping("/task_state/{state}")
    Task getTaskState(@PathVariable Integer state){
        return repository.findTaskByState(state);
    }

}
