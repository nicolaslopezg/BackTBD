package com.example.demo.controllers;


import com.example.demo.models.Role;
import com.example.demo.models.User;
import com.example.demo.repositories.RoleRepository;
import com.example.demo.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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
    @Autowired
    UserRepository userRepository;

    @GetMapping
    @ResponseBody
    public List<Role> getAllRoles() {
        return roleRepository.findAll();
    }

    @GetMapping(value = "/{id}")
    @ResponseBody
    public int getTipoById(@PathVariable Long id) {
        Role role = roleRepository.findRoleById(id);
        return role.getType();
    }

    @GetMapping(value = "/tipo/{tipo}")
    @ResponseBody
    public Role getRoleByTipo(@PathVariable Integer tipo){
        return roleRepository.findRoleByType(tipo);
    }

    @PostMapping("/create")
    @ResponseBody
    public Role create(@RequestBody Map<String, Object> jsonData) throws ParseException {
        List<HashMap<String, String>> result = new ArrayList<HashMap<String, String>>();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        HashMap<String, String> map = new HashMap<>();


        Role role = roleRepository.findRoleByType(Integer.parseInt(jsonData.get("tipo").toString()));
        if(role == null) {
                return roleRepository.save(new Role(Integer.parseInt(jsonData.get("tipo").toString()),
                        jsonData.get("descripcion").toString()));

        }
        else {
            map.put("status", "401");
            System.out.println("Role with this code already exist.");
            map.put("message", "Role with this code already exist.");
            map.put("item", Integer.toString(role.getType()));
            result.add(map);
            return new Role();
        }
    }

    @PostMapping("/update/{tipo}")
    @ResponseBody
    public ResponseEntity<Object> update(@PathVariable int tipo, @RequestBody Map<String, Object> jsonData) throws ParseException {
        List<HashMap<String, String>> result = new ArrayList<HashMap<String, String>>();
        HashMap<String, String> map = new HashMap<>();
        Role role = roleRepository.findRoleByType(tipo);
        if(role == null) {
            map.put("status", "404");
            map.put("message", "Role does not exist!.");
            map.put("item", "");
            result.add(map);
            return ResponseEntity.notFound().build();
        }
        else {
            role.setType(Integer.parseInt(jsonData.get("tipo").toString()));
            role.setDescription(jsonData.get("descripcion").toString());
            roleRepository.save(role);
            map.put("status", "200");
            map.put("message", "OK");
            map.put("item",Integer.toString(role.getType()));
            result.add(map);
            return ResponseEntity.noContent().build();
        }
    }

    @CrossOrigin(origins = "*")
    @PostMapping("/delete/{id}")
    @ResponseBody
    public void delete(@PathVariable Long id) throws ParseException {
        List<HashMap<String, String>> result = new ArrayList<HashMap<String, String>>();
        HashMap<String, String> map = new HashMap<>();
        Role role = roleRepository.findRoleById(id);
        if(role == null) {
            map.put("status", "404");
            map.put("message", "Role does not exist!.");
            System.out.println("Role does not exist!.");
            map.put("item", "");
            result.add(map);
            return;
        }
        else {
            String erasedUser = Integer.toString(role.getType());
            roleRepository.deleteById(id);
            map.put("status", "200");
            map.put("message", "OK, role erased!.");
            map.put("item", erasedUser);
            result.add(map);
            return;
        }
    }
}
