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

@CrossOrigin(origins = "*")
@RestController
public class DistrictController {

    @Autowired
    DistrictRepository districtRepository;
    @Autowired
    CityRepository cityRepository;

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
    public List<HashMap<String, String>> insertDistrict(@RequestBody Map<String, Object> jsonData) {
        List<HashMap<String, String>> result = new ArrayList<HashMap<String, String>>();
        HashMap<String, String> map = new HashMap<>();
        Long idCity = Long.parseLong(jsonData.get("city").toString());
        City city =  cityRepository.findCityById(idCity);
        if(city != null){
            districtRepository.save(new District(jsonData.get("name").toString(),city));
            map.put("status", "201");
            map.put("message", "District added");
            result.add(map);
            return result;
        }
        else {
            map.put("status", "401");
            map.put("message", "User does not exist!.");
            result.add(map);
            return result;
        }
    }
}
