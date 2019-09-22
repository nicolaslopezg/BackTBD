package com.example.demo.controllers;

import com.example.demo.models.VoluntaryTask;
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

    @GetMapping("/voluntary_tasks")
    public List<VoluntaryTask> getAll(){return repository.findAll(); }

    //No parte de la id 1.
    @PostMapping("/voluntary_tasks")
    VoluntaryTask insertVoluntaryDimension(@RequestBody VoluntaryTask newVoluntaryTask){ return repository.save(newVoluntaryTask); }

    @GetMapping("/voluntary_tasks/{id}")
    VoluntaryTask getVoluntaryDimensionId(@PathVariable Long id){
        return repository.findVDById(id);
    }



}

