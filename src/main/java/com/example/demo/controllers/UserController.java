package com.example.demo.controllers;

import com.example.demo.models.Role;
import com.example.demo.repositories.RoleRepository;
import com.example.demo.repositories.UserRepository;
import com.example.demo.models.User;
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
@RequestMapping("/users")
public class UserController {

    // Repositorios.
    @Autowired
    UserRepository userRepository;
    @Autowired
    RoleRepository roleRepository;

    // Servicios.
    @GetMapping
    @ResponseBody
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @GetMapping(value = "/{id}")
    @ResponseBody
    public User getNombreUserById(@PathVariable Long id) {
        User user = userRepository.findUserById(id);
        return user;
    }

    @GetMapping(value = "/rut/{rut}")
    @ResponseBody
    public User getUserByRut(@PathVariable Integer rut){
        return userRepository.findUserByRut(rut);
    }

    @PostMapping("/create")
    @ResponseBody
    public User create(@RequestBody Map<String, Object> jsonData) throws ParseException {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

        Long idRole = Long.parseLong(jsonData.get("role").toString());
        try{
            Role role = roleRepository.findRoleById(idRole);
            try{
                User usuario = userRepository.findUserByRut(Integer.parseInt(jsonData.get("rut").toString()));
                userRepository.save(new User(Integer.parseInt(jsonData.get("rut").toString()),jsonData.get("name").toString(), jsonData.get("lastname").toString(), jsonData.get("mail").toString(),role));
            }
            catch (NullPointerException e){
                System.out.println("User does not exist!!");
            }
        }
        catch (NullPointerException e) {
            System.out.println("Role does not exist!!");
        }
        return new User();
    }

    @PostMapping("/update/{rutUsuario}")
    @ResponseBody
    public ResponseEntity<Object> update(@PathVariable int rutUsuario, @RequestBody Map<String, Object> jsonData) throws ParseException {
        User usuario = userRepository.findUserByRut(rutUsuario);
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        if(usuario == null) {
            System.out.println("User does not exist!!");
            return ResponseEntity.notFound().build();
        }
        else {
            usuario.setRut(Integer.parseInt(jsonData.get("rut").toString()));
            usuario.setName(jsonData.get("nombre").toString());
            usuario.setLastname(jsonData.get("apellido").toString());
            usuario.setMail(jsonData.get("correo").toString());
            usuario.setBirthDate(formatter.parse(jsonData.get("fechaNacimiento").toString()));
            userRepository.save(usuario);
            return ResponseEntity.noContent().build();
        }
    }

    @CrossOrigin(origins = "*")
    @PostMapping("/delete/{id}")
    @ResponseBody
    public void delete(@PathVariable Long id) throws ParseException {
        User usuario = userRepository.findUserById(id);
        if(usuario == null) {
            System.out.println("User does not exist!!");
            return;
        }
        else {
                userRepository.deleteById(id);
        }
    }

    @GetMapping("/getCordinatorUser")
    @ResponseBody
    public List<User> getCordinators() {
        Role role = roleRepository.findRoleById(Long.valueOf(2));
        List<User> users = userRepository.findUserByRole(role);
        return users;
    }
}
