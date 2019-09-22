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

    //No parte de la id 1.
    @PostMapping("/voluntary_tasks")
    VoluntaryTask insertVoluntaryDimension(@RequestBody VoluntaryTask newVoluntaryTask){ return repository.save(newVoluntaryTask); }

    @GetMapping("/voluntary_tasks/{id}")
    VoluntaryTask getVoluntaryDimensionId(@PathVariable Long id){
        return repository.findVDById(id);
    }

    @PostMapping("/voluntary_tasks/assign")
    @ResponseBody
    public List<HashMap<String, String>> assign(@RequestBody Map<String, Object> jsonData) throws ParseException {
        List<HashMap<String, String>> result = new ArrayList<HashMap<String, String>>();
        HashMap<String, String> map = new HashMap<>();
        Long idTask = Long.parseLong(jsonData.get("idTask").toString());
        int rutVoluntary = Integer.parseInt(jsonData.get("rutVoluntary").toString());
        Voluntary vol = voluntaryRepository.findVoluntaryByRut(rutVoluntary);
        Task task = taskRepository.findTaskById(idTask);
        if(vol != null){
            if(task != null){
                repository.save(new VoluntaryTask(task,vol));
                map.put("status", "201");
                map.put("message", "VoluntaryTask added");
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

}

