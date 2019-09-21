package com.example.demo.Controllers;

import com.example.demo.Repositories.UserRepository;
import com.example.demo.Models.User;
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
@RequestMapping("/users")
public class UserController {

    @Autowired
    UserRepository userRepository;

    @GetMapping
    @ResponseBody
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @GetMapping(value = "/{id}")
    @ResponseBody
    public String getNombreUserById(@PathVariable Long id) {
        User user = userRepository.findUserByIdUser(id);
        return user.getNombreUser();
    }

    @GetMapping(value = "/rut/{rut}")
    @ResponseBody
    public User getUserByRut(@PathVariable Integer rut){
        return userRepository.findUserByRut(rut);
    }

    @PostMapping("/create")
    @ResponseBody
    public List<HashMap<String, String>> create(@RequestBody Map<String, Object> jsonData) throws ParseException {
        List<HashMap<String, String>> result = new ArrayList<HashMap<String, String>>();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        HashMap<String, String> map = new HashMap<>();

        User usuario = userRepository.findUserByRut(Integer.parseInt(jsonData.get("rut").toString()));
        if(usuario == null) {
            userRepository.save(new User(Integer.parseInt(jsonData.get("rut").toString()),
                    jsonData.get("nombre").toString(),
                    jsonData.get("apellido").toString(),
                    jsonData.get("correo").toString(),
                    formatter.parse(jsonData.get("fechaNacimiento").toString())));
            System.out.println(jsonData);
            map.put("status", "201");
            map.put("message", "OK");
            result.add(map);
            return result;
        }
        else {
            map.put("status", "401");
            map.put("message", "User with this rut already exist.");
            map.put("item", usuario.getNombreUser());
            result.add(map);
            return result;
        }
    }

    @PostMapping("/update/{rutUsuario}")
    @ResponseBody
    public List<HashMap<String, String>> update(@PathVariable int rutUsuario, @RequestBody Map<String, Object> jsonData) throws ParseException {
        List<HashMap<String, String>> result = new ArrayList<HashMap<String, String>>();
        HashMap<String, String> map = new HashMap<>();
        User usuario = userRepository.findUserByRut(rutUsuario);
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        if(usuario == null) {
            map.put("status", "404");
            map.put("message", "User does not exist!.");
            map.put("item", "");
            result.add(map);
            return result;
        }
        else {
            usuario.setRut(Integer.parseInt(jsonData.get("rut").toString()));
            usuario.setNombreUser(jsonData.get("nombre").toString());
            usuario.setApellidoUser(jsonData.get("apellido").toString());
            usuario.setCorreoUser(jsonData.get("correo").toString());
            usuario.setFechaNacimiento(formatter.parse(jsonData.get("fechaNacimiento").toString()));
            userRepository.save(usuario);
            map.put("status", "200");
            map.put("message", "OK");
            map.put("item", usuario.getNombreUser());
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
        User usuario = userRepository.findUserByIdUser(id);
        if(usuario == null) {
            map.put("status", "404");
            map.put("message", "User does not exist!.");
            map.put("item", "");
            result.add(map);
            return result;
        }
        else {
            String erasedUser = usuario.getNombreUser();
            userRepository.deleteById(id);
            map.put("status", "200");
            map.put("message", "OK, user erased!.");
            map.put("item", erasedUser);
            result.add(map);
            return result;
        }
    }
}
