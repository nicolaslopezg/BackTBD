package com.example.demo.controllers;

import com.example.demo.models.*;
import com.example.demo.repositories.*;
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
    @Autowired
    DimensionRepository dimensionRepository;
    @Autowired
    VoluntaryDimensionRepository voluntaryDimensionRepository;

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

    @CrossOrigin(origins = "*")
    @PostMapping("/acceptTask")
    @ResponseBody
    public List<HashMap<String, String>> acceptTask(@RequestBody Map<String, Object> jsonData) throws ParseException {
        List<HashMap<String, String>> result = new ArrayList<HashMap<String, String>>();
        HashMap<String, String> map = new HashMap<>();
        int rut = Integer.parseInt(jsonData.get("rut").toString());
        Voluntary vol = voluntaryRepository.findVoluntaryByRut(rut);
        if (vol == null) {
            map.put("status", "404");
            map.put("message", "Voluntary does not exist!.");
            map.put("item", "");
            result.add(map);
            return result;
        } else {
            if (vol.getAsignado()== true){
                map.put("status", "404");
                map.put("message", "Voluntary already with task!.");
                map.put("item", "");
                result.add(map);
                return result;
            }
            Long idTask = Long.parseLong(jsonData.get("idTask").toString());
            Task task = taskRepository.findTaskById(idTask);
            List<VoluntaryTask> asignaciones = voluntaryTaskRepository.findVoluntaryTasksByTask(task);
            for (VoluntaryTask asignacion: asignaciones) {
                if (asignacion.getVoluntary().equals(vol)){
                    if (asignacion.getEstado()==2){     //Ya fue rechazada
                        map.put("status", "404");
                        map.put("message", "Task  already rejected!.");
                        map.put("item", "");
                        result.add(map);
                        return result;
                    }
                    else if(asignacion.getEstado()==1){    //Ya fue aceptada
                        map.put("status", "404");
                        map.put("message", "Task  already accepted!.");
                        map.put("item", "");
                        result.add(map);
                        return result;
                    }
                    else if (asignacion.getEstado()==0){    // Asignación por aceptar
                        vol.setAsignado(true);
                        voluntaryRepository.save(vol);
                        asignacion.setEstado(1);
                        voluntaryTaskRepository.save(asignacion);
                        System.out.println(jsonData);
                        map.put("status", "201");
                        map.put("message", "OK, Task accepted!");
                        result.add(map);
                        return result;
                    }
                }
            }
            map.put("status", "404");
            map.put("message", "Task  not found for this user!.");
            map.put("item", "");
            result.add(map);
            return result;
        }
    }

    @CrossOrigin(origins = "*")
    @PostMapping("/rejectTask")
    @ResponseBody
    public List<HashMap<String, String>> rejectTask(@RequestBody Map<String, Object> jsonData) throws ParseException {
        List<HashMap<String, String>> result = new ArrayList<HashMap<String, String>>();
        HashMap<String, String> map = new HashMap<>();
        int rut = Integer.parseInt(jsonData.get("rut").toString());
        Voluntary vol = voluntaryRepository.findVoluntaryByRut(rut);
        if (vol == null) {
            map.put("status", "404");
            map.put("message", "Voluntary does not exist!.");
            map.put("item", "");
            result.add(map);
            return result;
        } else {
            if (vol.getAsignado()== true){
                map.put("status", "404");
                map.put("message", "Voluntary already with task!.");
                map.put("item", "");
                result.add(map);
                return result;
            }
            Long idTask = Long.parseLong(jsonData.get("idTask").toString());
            Task task = taskRepository.findTaskById(idTask);
            List<VoluntaryTask> asignaciones = voluntaryTaskRepository.findVoluntaryTasksByTask(task);
            for (VoluntaryTask asignacion: asignaciones) {
                if (asignacion.getVoluntary().equals(vol)){
                    if (asignacion.getEstado()==2){     //Ya fue rechazada
                        map.put("status", "404");
                        map.put("message", "Task  already rejected!.");
                        map.put("item", "");
                        result.add(map);
                        return result;
                    }
                    else if(asignacion.getEstado()==1){    //Ya fue aceptada
                        map.put("status", "404");
                        map.put("message", "Task  already accepted!.");
                        map.put("item", "");
                        result.add(map);
                        return result;
                    }
                    else if (asignacion.getEstado()==0){    // Asignación por aceptar
                        asignacion.setEstado(2);
                        voluntaryTaskRepository.save(asignacion);
                        System.out.println(jsonData);
                        map.put("status", "201");
                        map.put("message", "OK, Task rejected!");
                        result.add(map);
                        return result;
                    }
                }
            }
            map.put("status", "404");
            map.put("message", "Task  not found for this user!.");
            map.put("item", "");
            result.add(map);
            return result;
        }
    }

    @CrossOrigin(origins = "*")
    @PostMapping("/finishTask")
    @ResponseBody
    public List<HashMap<String, String>> finishTask(@RequestBody Map<String, Object> jsonData) throws ParseException {
        List<HashMap<String, String>> result = new ArrayList<HashMap<String, String>>();
        HashMap<String, String> map = new HashMap<>();
        int rut = Integer.parseInt(jsonData.get("rut").toString());
        Voluntary vol = voluntaryRepository.findVoluntaryByRut(rut);
        if (vol == null) {
            map.put("status", "404");
            map.put("message", "Voluntary does not exist!.");
            map.put("item", "");
            result.add(map);
            return result;
        } else {
            if (vol.getAsignado()== false){
                map.put("status", "404");
                map.put("message", "Voluntary doesn't have a task!.");
                map.put("item", "");
                result.add(map);
                return result;
            }
            Long idTask = Long.parseLong(jsonData.get("idTask").toString());
            Task task = taskRepository.findTaskById(idTask);
            List<VoluntaryTask> asignaciones = voluntaryTaskRepository.findVoluntaryTasksByTask(task);
            for (VoluntaryTask asignacion: asignaciones) {
                if (asignacion.getVoluntary().equals(vol)){
                    if (asignacion.getEstado()==2){     //Ya fue rechazada
                        map.put("status", "404");
                        map.put("message", "Task  already rejected!.");
                        map.put("item", "");
                        result.add(map);
                        return result;
                    }
                    else if(asignacion.getEstado()==1){    //Ya fue aceptada
                        asignacion.setEstado(3);
                        voluntaryTaskRepository.save(asignacion);
                        vol.setAsignado(false);
                        voluntaryRepository.save(vol);
                        task.setState(1);
                        taskRepository.save(task);
                        System.out.println(jsonData);
                        map.put("status", "201");
                        map.put("message", "OK, Task finished!!");
                        result.add(map);
                        return result;
                    }
                    else if (asignacion.getEstado()==0){    // Asignación por aceptar
                        map.put("status", "404");
                        map.put("message", "Task  not accepted yet!!.");
                        map.put("item", "");
                        result.add(map);
                        return result;
                    }
                }
            }
            map.put("status", "404");
            map.put("message", "Task  not found for this user!.");
            map.put("item", "");
            result.add(map);
            return result;
        }
    }


    @GetMapping("/topVolunteers/{name}")
    @ResponseBody
    public List<VoluntaryDimension> getTopVoluntaries(@PathVariable String name){
        // Obtengo la Dimension.
        Dimension dimension = dimensionRepository.findDimensionByName(name);
        // De la tabla Voluntary-Dimension, obtengo sólo los campos que tienen la Dimension consultada.
        List<VoluntaryDimension> voluntaryDimensions = voluntaryDimensionRepository.findVoluntaryDimensionByDimensionOrderByQuantityDesc(dimension);
        return voluntaryDimensions;
    }
}