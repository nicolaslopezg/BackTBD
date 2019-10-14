package com.example.demo.controllers;

import com.example.demo.models.Dimension;
import com.example.demo.models.VoluntaryDimension;
import com.example.demo.models.Voluntary;
import com.example.demo.repositories.VoluntaryDimensionRepository;
import com.example.demo.repositories.VoluntaryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/")
@CrossOrigin(origins = "*")

public class VoluntaryDimensionController {

    @Autowired
    VoluntaryDimensionRepository repository;

    @Autowired
    VoluntaryRepository voluntaryRepository;

    @GetMapping("/voluntary_dimensions")
    public List<VoluntaryDimension> getAll(){return repository.findAll(); }

    //No parte de la id 1.
    @PostMapping("/voluntary_dimensions")
    VoluntaryDimension insertVoluntaryDimension(@RequestBody VoluntaryDimension newVoluntaryDimension){ return repository.save(newVoluntaryDimension); }

    @GetMapping("/voluntary_dimension_id/{id}")
    VoluntaryDimension getVoluntaryDimensionId(@PathVariable Long id){
        return repository.findVDById(id);
    }

}
