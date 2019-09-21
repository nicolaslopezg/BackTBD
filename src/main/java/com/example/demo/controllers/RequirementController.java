package com.example.demo.Controllers;


import com.example.demo.Models.Requirement;
import com.example.demo.Repositories.RequirementRepository;
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
        Requirement requirement = requirementRepository.findRequirementByIdRequirement(id);
        return requirement.getTipo();
    }

    @GetMapping(value = "/tipo/{tipo}")
    @ResponseBody
    public Requirement getRequirementByTipo(@PathVariable Integer tipo){
        return requirementRepository.findRequirementByTipo(tipo);
    }

    @PostMapping("/create")
    @ResponseBody
    public List<HashMap<String, String>> create(@RequestBody Map<String, Object> jsonData) throws ParseException {
        List<HashMap<String, String>> result = new ArrayList<HashMap<String, String>>();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        HashMap<String, String> map = new HashMap<>();

        Requirement role = requirementRepository.findRequirementByTipo(Integer.parseInt(jsonData.get("tipo").toString()));
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
            map.put("item", Integer.toString(role.getTipo()));
            result.add(map);
            return result;
        }
    }

    @PostMapping("/update/{tipo}")
    @ResponseBody
    public List<HashMap<String, String>> update(@PathVariable int tipo, @RequestBody Map<String, Object> jsonData) throws ParseException {
        List<HashMap<String, String>> result = new ArrayList<HashMap<String, String>>();
        HashMap<String, String> map = new HashMap<>();
        Requirement req = requirementRepository.findRequirementByTipo(tipo);
        if(req == null) {
            map.put("status", "404");
            map.put("message", "Requirement does not exist!.");
            map.put("item", "");
            result.add(map);
            return result;
        }
        else {
            req.setTipo(Integer.parseInt(jsonData.get("tipo").toString()));
            req.setDescripcion(jsonData.get("descripcion").toString());
            requirementRepository.save(req);
            map.put("status", "200");
            map.put("message", "OK");
            map.put("item",Integer.toString(req.getTipo()));
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
        Requirement req = requirementRepository.findRequirementByIdRequirement(id);
        if(req == null) {
            map.put("status", "404");
            map.put("message", "Role does not exist!.");
            map.put("item", "");
            result.add(map);
            return result;
        }
        else {
            String erasedUser = Integer.toString(req.getTipo());
            requirementRepository.deleteById(id);
            map.put("status", "200");
            map.put("message", "OK, requirement erased!.");
            map.put("item", erasedUser);
            result.add(map);
            return result;
        }
    }
}
