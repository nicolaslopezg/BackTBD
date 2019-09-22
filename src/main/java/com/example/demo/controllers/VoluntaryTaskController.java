package com.example.demo.controllers;

import com.example.demo.models.Task;
import com.example.demo.models.Voluntary;
import com.example.demo.models.VoluntaryTask;
import com.example.demo.repositories.TaskRepository;
import com.example.demo.repositories.VoluntaryRepository;
import com.example.demo.repositories.VoluntaryTaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/")
@CrossOrigin(origins = "*")

public class VoluntaryTaskController {

    @Autowired
    VoluntaryTaskRepository repository;
    @Autowired
    VoluntaryRepository voluntaryRepository;
    @Autowired
    TaskRepository taskRepository;

    @GetMapping("/voluntary_tasks")
    public List<VoluntaryTask> getAll(){return repository.findAll(); }

    @PostMapping("/voluntary_tasks/create")
    @ResponseBody
    public List<HashMap<String, String>> insertVoluntary_Task(@RequestBody Map<String, Object> jsonData){

        List<HashMap<String, String>> result = new ArrayList<HashMap<String, String>>();
        HashMap<String, String> map = new HashMap<>();

        Long idVoluntary = Long.parseLong(jsonData.get("voluntary").toString());
        Long idTask = Long.parseLong(jsonData.get("task").toString());

        Voluntary voluntary = voluntaryRepository.findVoluntaryByIdVoluntary(idVoluntary);
        Task task;
        if(voluntary != null){
            task = taskRepository.findTasksById(idTask);
            if(task != null){
                repository.save(new VoluntaryTask(0, task,voluntary));
                map.put("status", "201");
                map.put("message", "Voluntary_Task added");
                result.add(map);
                return result;
            } else {
                map.put("status", "401");
                map.put("message", "Task does not exist!.");
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

    @GetMapping("/voluntary_tasks/{id}")
    VoluntaryTask getVoluntaryDimensionId(@PathVariable Long id){
        return repository.findVDById(id);
    }



}

