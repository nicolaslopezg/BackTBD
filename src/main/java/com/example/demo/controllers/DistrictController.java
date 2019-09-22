package com.example.demo.controllers;

import com.example.demo.models.District;
import com.example.demo.repositories.DistrictRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*")
@RestController
public class DistrictController {

    @Autowired
    DistrictRepository districtRepository;

    @GetMapping("/districts")
    @ResponseBody
    public List<District> getAllDistricts() {
        return districtRepository.findAll();
    }

    @GetMapping("/districts/{id}")
    @ResponseBody
    public District getCity(@PathVariable Long id) { return districtRepository.findCityById(id); }

    @GetMapping("/districtByName/{name}")
    @ResponseBody
    public District getCityByName(@PathVariable String name) { return districtRepository.findCityByName(name); }

}
