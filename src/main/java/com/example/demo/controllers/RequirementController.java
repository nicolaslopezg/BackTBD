package com.example.demo.controllers;


import com.example.demo.models.Requirement;
import com.example.demo.repositories.RequirementRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/requirements")
public class RequirementController {
    @Autowired
    RequirementRepository requirementRepository;

    @GetMapping
    @ResponseBody
    public List<Requirement> getAllRequirements() {
        return requirementRepository.findAll();
    }

    @GetMapping(value = "/{id}")
    @ResponseBody
    public int getTipoById(@PathVariable Long id) {
        Requirement requirement = requirementRepository.findRequirementById(id);
        return requirement.getType();
    }

    @GetMapping(value = "/tipo/{tipo}")
    @ResponseBody
    public Requirement getRequirementByTipo(@PathVariable Integer tipo){
        return requirementRepository.findRequirementByType(tipo);
    }

    @PostMapping("/create")
    @ResponseBody
    public List<HashMap<String, String>> create(@RequestBody Map<String, Object> jsonData) throws ParseException {
        List<HashMap<String, String>> result = new ArrayList<HashMap<String, String>>();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        HashMap<String, String> map = new HashMap<>();

        Requirement role = requirementRepository.findRequirementByType(Integer.parseInt(jsonData.get("tipo").toString()));
        if(role == null) {
            requirementRepository.save(new Requirement(Integer.parseInt(jsonData.get("tipo").toString()),
                    jsonData.get("descripcion").toString()));
            System.out.println(jsonData);
            map.put("status", "201");
            map.put("message", "OK");
            result.add(map);
            return result;
        }
        else {
            map.put("status", "401");
            map.put("message", "Requirement with this code already exist.");
            map.put("item", Integer.toString(role.getType()));
            result.add(map);
            return result;
        }
    }

    @PostMapping("/update/{tipo}")
    @ResponseBody
    public List<HashMap<String, String>> update(@PathVariable int tipo, @RequestBody Map<String, Object> jsonData) throws ParseException {
        List<HashMap<String, String>> result = new ArrayList<HashMap<String, String>>();
        HashMap<String, String> map = new HashMap<>();
        Requirement req = requirementRepository.findRequirementByType(tipo);
        if(req == null) {
            map.put("status", "404");
            map.put("message", "Requirement does not exist!.");
            map.put("item", "");
            result.add(map);
            return result;
        }
        else {
            req.setType(Integer.parseInt(jsonData.get("tipo").toString()));
            req.setDescription(jsonData.get("descripcion").toString());
            requirementRepository.save(req);
            map.put("status", "200");
            map.put("message", "OK");
            map.put("item",Integer.toString(req.getType()));
            result.add(map);
            return result;
        }
    }

    @CrossOrigin(origins = "*")
    @PostMapping("/delete/{id}")
    @ResponseBody
    public List<HashMap<String, String>> delete(@PathVariable Long id) throws ParseException {
        List<HashMap<String, String>> result = new ArrayList<HashMap<String, String>>();
        HashMap<String, String> map = new HashMap<>();
        Requirement req = requirementRepository.findRequirementById(id);
        if(req == null) {
            map.put("status", "404");
            map.put("message", "Role does not exist!.");
            map.put("item", "");
            result.add(map);
            return result;
        }
        else {
            String erasedUser = Integer.toString(req.getType());
            requirementRepository.deleteById(id);
            map.put("status", "200");
            map.put("message", "OK, requirement erased!.");
            map.put("item", erasedUser);
            result.add(map);
            return result;
        }
    }
}
