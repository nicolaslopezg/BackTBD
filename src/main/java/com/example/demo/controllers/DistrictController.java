package com.example.demo.controllers;

import com.example.demo.models.City;
import com.example.demo.models.District;
import com.example.demo.repositories.CityRepository;
import com.example.demo.repositories.DistrictRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

// Servicio Rest de Distrito.
@CrossOrigin(origins = "*")
@RestController
public class DistrictController {

    // Repositorios a utilizar.
    @Autowired
    DistrictRepository districtRepository;
    @Autowired
    CityRepository cityRepository;

    // Servicios.
    @GetMapping("/districts")
    @ResponseBody
    public List<District> getAllDistricts() {
        return districtRepository.findAll();
    }

    @GetMapping("/districts/{id}")
    @ResponseBody
    public District getCity(@PathVariable Long id) { return districtRepository.findDistrictById(id); }

    @GetMapping("/districtByName/{name}")
    @ResponseBody
    public District getCityByName(@PathVariable String name) { return districtRepository.findDistrictByName(name); }

    @PostMapping("/districts/create")
    @ResponseBody
    public District insertDistrict(@RequestBody Map<String, Object> jsonData) {
        Long idCity = Long.parseLong(jsonData.get("city").toString());

        try {
            City city =  cityRepository.findCityById(idCity);
            return districtRepository.save(new District(city,jsonData.get("name").toString()));
        }
        catch (NullPointerException e) {
            System.out.println("City doest no exist!!");
        }
        return new District();
    }
}
