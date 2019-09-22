package com.example.demo.controllers;

import com.example.demo.models.City;
import com.example.demo.models.Role;
import com.example.demo.repositories.CityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*")
@RestController
public class CityController {

    @Autowired
    CityRepository cityRepository;

    @GetMapping("/cities")
    @ResponseBody
    public List<City> getAllRoles() {
        return cityRepository.findAll();
    }

    @GetMapping("/cities/{id}")
    @ResponseBody
    public City getCity(@RequestBody Long id) { return cityRepository.findCityById(id); }

    @GetMapping
    @ResponseBody
    public City getCityByName(@RequestBody String name) { return cityRepository.findCityByName(name); }

}
