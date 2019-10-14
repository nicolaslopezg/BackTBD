package com.example.demo.controllers;

import com.example.demo.models.City;
import com.example.demo.models.Role;
import com.example.demo.repositories.CityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

// Servicio REST de Ciudad.
@CrossOrigin(origins = "*")
@RestController
public class CityController {

    // Repositorios a utilizar.
    @Autowired
    CityRepository cityRepository;

    // Servicios
    @GetMapping("/cities")
    @ResponseBody
    public List<City> getAllCities() {
        return cityRepository.findAll();
    }

    @GetMapping("/cities/{id}")
    @ResponseBody
    public City getCity(@RequestBody Long id) { return cityRepository.findCityById(id); }

    @GetMapping
    @ResponseBody
    public City getCityByName(@RequestBody String name) { return cityRepository.findCityByName(name); }

}
