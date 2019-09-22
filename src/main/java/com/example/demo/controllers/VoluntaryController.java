package com.example.demo.controllers;

import com.example.demo.models.Task;
import com.example.demo.models.Voluntary;
import com.example.demo.models.VoluntaryTask;
import com.example.demo.repositories.TaskRepository;
import com.example.demo.repositories.VoluntaryRepository;
import com.example.demo.repositories.VoluntaryTaskRepository;
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
@RequestMapping("/volunteers")
public class VoluntaryController {

    @Autowired
    VoluntaryRepository voluntaryRepository;
    @Autowired
    VoluntaryTaskRepository voluntaryTaskRepository;
    @Autowired
    TaskRepository taskRepository;

    @GetMapping
    @ResponseBody
    public List<Voluntary> getAllVolunteers() { return voluntaryRepository.findAll(); }

    @GetMapping(value = "/{id}")
    @ResponseBody
    public String getNombreVoluntaryById(@PathVariable Long id) {
        Voluntary user = voluntaryRepository.findVoluntaryByIdVoluntary(id);
        return user.getNombre();
    }

    @GetMapping(value = "/rut/{rut}")
    @ResponseBody
    public Voluntary getUserByRut(@PathVariable Integer rut) { return voluntaryRepository.findVoluntaryByRut(rut); }


    @GetMapping("/byTask/{id}")
    @ResponseBody
    public List<Voluntary> getVoluntaryByTask(@PathVariable Long id){
        Task task = taskRepository.findTasksById(id);
        List<Voluntary> result = new ArrayList<Voluntary>();
        List<VoluntaryTask> voluntaryTask = voluntaryTaskRepository.findVoluntaryTasksByTask(task);
        for (int i = 0; i < voluntaryTask.size(); i++){
            result.add(voluntaryTask.get(i).getVoluntary());
        }
        return result;
    }

    @PostMapping("/create")
    @ResponseBody
    public List<HashMap<String, String>> create(@RequestBody Map<String, Object> jsonData) throws ParseException {
        List<HashMap<String, String>> result = new ArrayList<HashMap<String, String>>();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        HashMap<String, String> map = new HashMap<>();

        Voluntary vol = voluntaryRepository.findVoluntaryByRut(Integer.parseInt(jsonData.get("rut").toString()));
        if(vol == null) {
            voluntaryRepository.save(new Voluntary(Integer.parseInt(jsonData.get("rut").toString()),
                    jsonData.get("nombre").toString(),
                    jsonData.get("apellido").toString(),
                    jsonData.get("correo").toString(),
                    formatter.parse(jsonData.get("fechaNacimiento").toString()),
                    Boolean.parseBoolean(jsonData.get("asignado").toString())));
            System.out.println(jsonData);
            map.put("status", "201");
            map.put("message", "OK");
            result.add(map);
            return result;
        }
        else {
            map.put("status", "401");
            map.put("message", "Voluntary with this rut already exist.");
            map.put("item", vol.getNombre());
            result.add(map);
            return result;
        }
    }

    @PostMapping("/update/{rut}")
    @ResponseBody
    public List<HashMap<String, String>> update(@PathVariable int rut, @RequestBody Map<String, Object> jsonData) throws ParseException {
        List<HashMap<String, String>> result = new ArrayList<HashMap<String, String>>();
        HashMap<String, String> map = new HashMap<>();
        Voluntary vol = voluntaryRepository.findVoluntaryByRut(rut);
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        if(vol == null) {
            map.put("status", "404");
            map.put("message", "Voluntary does not exist!.");
            map.put("item", "");
            result.add(map);
            return result;
        }
        else {
            vol.setRut(Integer.parseInt(jsonData.get("rut").toString()));
            vol.setNombre(jsonData.get("nombre").toString());
            vol.setApellido(jsonData.get("apellido").toString());
            vol.setCorreo(jsonData.get("correo").toString());
            vol.setFechaNacimiento(formatter.parse(jsonData.get("fechaNacimiento").toString()));
            vol.setAsignado(Boolean.parseBoolean(jsonData.get("asignado").toString()));
            voluntaryRepository.save(vol);
            map.put("status", "200");
            map.put("message", "OK");
            map.put("item", vol.getNombre());
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
        Voluntary vol = voluntaryRepository.findVoluntaryByIdVoluntary(id);
        if(vol == null) {
            map.put("status", "404");
            map.put("message", "Voluntary does not exist!.");
            map.put("item", "");
            result.add(map);
            return result;
        }
        else {
            String erasedUser = vol.getNombre();
            voluntaryRepository.deleteById(id);
            map.put("status", "200");
            map.put("message", "OK, voluntary erased!.");
            map.put("item", erasedUser);
            result.add(map);
            return result;
        }
    }
}