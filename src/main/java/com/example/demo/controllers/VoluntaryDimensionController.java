package com.example.demo.Controllers;

import com.example.demo.Models.VoluntaryDimension;
import com.example.demo.Repositories.VoluntaryDimensionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/")
@CrossOrigin(origins = "*")

public class VoluntaryDimensionController {

    @Autowired
    VoluntaryDimensionRepository repository;

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
