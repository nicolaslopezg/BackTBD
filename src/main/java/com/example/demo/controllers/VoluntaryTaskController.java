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
    public VoluntaryTask insertVoluntary_Task(@RequestBody Map<String, Object> jsonData){

        Long idVoluntary = Long.parseLong(jsonData.get("voluntary").toString());
        Long idTask = Long.parseLong(jsonData.get("task").toString());

        Voluntary voluntary = voluntaryRepository.findVoluntaryById(idVoluntary);
        Task task;
        if(voluntary != null){
            task = taskRepository.findTasksById(idTask);
            if(task != null){
                return repository.save(new VoluntaryTask(0, task,voluntary));
            } else {
                System.out.println("Task does not exist!!");
                return new VoluntaryTask();
            }
        } else {
            System.out.println("Voluntary does not exist!!");
            return new VoluntaryTask();
        }
    }

    @GetMapping("/voluntary_tasks/{id}")
    VoluntaryTask getVoluntaryDimensionId(@PathVariable Long id){
        return repository.findVDById(id);
    }

    @PostMapping("/voluntary_tasks/voluntary")
    @ResponseBody
    public List<HashMap<String, String>> voluntaryTask(@RequestBody Map<String, Object> jsonData) {
        List<HashMap<String, String>> result = new ArrayList<HashMap<String, String>>();
        HashMap<String, String> map = new HashMap<>();
        Long idVol = Long.parseLong(jsonData.get("id").toString());
        Voluntary vol = voluntaryRepository.findVoluntaryById(idVol);
        List<VoluntaryTask> tareas;
        if(vol == null) {
            map.put("status", "404");
            map.put("message", "Voluntary does not exist!.");
            map.put("item", "");
            result.add(map);
            return result;
        }
        else {
            tareas = repository.findAll();
            for (VoluntaryTask tarea: tareas) {
                if(tarea.getVoluntary().getId().equals(idVol)){
                    map.put("task", "");
                    map.put("idTask", tarea.getTask().getId().toString());
                    map.put("type",  tarea.getTask().getType());
                    map.put("description",  tarea.getTask().getDescription());
                    map.put("capacity",  tarea.getTask().getCapacity().toString());
                    map.put("status", tarea.getTask().getStatus().toString());
                    result.add(map);
                    map = new HashMap<>();
                }
            }
            if (tareas.size()==0){
                map.put("Sin", "Tareas");
                result.add(map);
            }

            return result;
        }
    }

}

