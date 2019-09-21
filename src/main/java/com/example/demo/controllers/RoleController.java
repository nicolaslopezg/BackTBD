package com.example.demo.controllers;


import com.example.demo.Models.Role;
import com.example.demo.repositories.RoleRepository;
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
@RequestMapping("/roles")
public class RoleController {
    @Autowired
    RoleRepository roleRepository;

    @GetMapping
    @ResponseBody
    public List<Role> getAllRoles() {
        return roleRepository.findAll();
    }

    @GetMapping(value = "/{id}")
    @ResponseBody
    public int getTipoById(@PathVariable Long id) {
        Role role = roleRepository.findRoleByIdRole(id);
        return role.getTipo();
    }

    @GetMapping(value = "/tipo/{tipo}")
    @ResponseBody
    public Role getRoleByTipo(@PathVariable Integer tipo){
        return roleRepository.findRoleByTipo(tipo);
    }

    @PostMapping("/create")
    @ResponseBody
    public List<HashMap<String, String>> create(@RequestBody Map<String, Object> jsonData) throws ParseException {
        List<HashMap<String, String>> result = new ArrayList<HashMap<String, String>>();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        HashMap<String, String> map = new HashMap<>();

        Role role = roleRepository.findRoleByTipo(Integer.parseInt(jsonData.get("tipo").toString()));
        if(role == null) {
            roleRepository.save(new Role(Integer.parseInt(jsonData.get("tipo").toString()),
                    jsonData.get("descripcion").toString()));
            System.out.println(jsonData);
            map.put("status", "201");
            map.put("message", "OK");
            result.add(map);
            return result;
        }
        else {
            map.put("status", "401");
            map.put("message", "Role with this code already exist.");
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
        Role role = roleRepository.findRoleByTipo(tipo);
        if(role == null) {
            map.put("status", "404");
            map.put("message", "Role does not exist!.");
            map.put("item", "");
            result.add(map);
            return result;
        }
        else {
            role.setTipo(Integer.parseInt(jsonData.get("tipo").toString()));
            role.setDescripcion(jsonData.get("descripcion").toString());
            roleRepository.save(role);
            map.put("status", "200");
            map.put("message", "OK");
            map.put("item",Integer.toString(role.getTipo()));
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
        Role role = roleRepository.findRoleByIdRole(id);
        if(role == null) {
            map.put("status", "404");
            map.put("message", "Role does not exist!.");
            map.put("item", "");
            result.add(map);
            return result;
        }
        else {
            String erasedUser = Integer.toString(role.getTipo());
            roleRepository.deleteById(id);
            map.put("status", "200");
            map.put("message", "OK, role erased!.");
            map.put("item", erasedUser);
            result.add(map);
            return result;
        }
    }
}
