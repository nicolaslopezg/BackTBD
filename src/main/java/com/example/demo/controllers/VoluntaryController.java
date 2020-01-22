package com.example.demo.controllers;

import com.example.demo.models.*;
import com.example.demo.repositories.*;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.Point;
import org.locationtech.spatial4j.distance.DistanceUtils;
import org.postgis.PGgeometry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.sql.*;

import static org.locationtech.jts.operation.distance.DistanceOp.isWithinDistance;

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
    @Autowired
    EmergencyRepository emergencyRepository;

    @GetMapping
    @ResponseBody
    public List<Voluntary> getAllVolunteers() { return voluntaryRepository.findAll(); }

    @GetMapping(value = "/{id}")
    @ResponseBody
    public String getNombreVoluntaryById(@PathVariable Long id) {
        Voluntary user = voluntaryRepository.findVoluntaryById(id);
        return user.getName();
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
    public Voluntary create(@RequestBody Map<String, Object> jsonData) throws ParseException {
        List<HashMap<String, String>> result = new ArrayList<HashMap<String, String>>();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        HashMap<String, String> map = new HashMap<>();

        Voluntary vol = voluntaryRepository.findVoluntaryByRut(Integer.parseInt(jsonData.get("rut").toString()));
        if(vol == null) {
            return voluntaryRepository.save(new Voluntary(Integer.parseInt(jsonData.get("rut").toString()),
                    jsonData.get("nombre").toString(),
                    jsonData.get("apellido").toString(),
                    jsonData.get("correo").toString(),
                    formatter.parse(jsonData.get("fechaNacimiento").toString()),
                    Boolean.parseBoolean(jsonData.get("asignado").toString())));
        }
        else {
            System.out.println("Voluntary with this rut already exist.");
            return new Voluntary();
        }
    }

    @PostMapping("/update/{rut}")
    @ResponseBody
    public ResponseEntity<Object> update(@PathVariable int rut, @RequestBody Map<String, Object> jsonData) throws ParseException {
        List<HashMap<String, String>> result = new ArrayList<HashMap<String, String>>();
        HashMap<String, String> map = new HashMap<>();
        Voluntary vol = voluntaryRepository.findVoluntaryByRut(rut);
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        if(vol == null) {
            map.put("status", "404");
            map.put("message", "Voluntary does not exist!.");
            map.put("item", "");
            result.add(map);
            return ResponseEntity.notFound().build();
        }
        else {
            vol.setRut(Integer.parseInt(jsonData.get("rut").toString()));
            vol.setName(jsonData.get("nombre").toString());
            vol.setLastname(jsonData.get("apellido").toString());
            vol.setMail(jsonData.get("correo").toString());
            vol.setBirthDate(formatter.parse(jsonData.get("fechaNacimiento").toString()));
            vol.setAsignated(Boolean.parseBoolean(jsonData.get("asignado").toString()));
            voluntaryRepository.save(vol);
            map.put("status", "200");
            map.put("message", "OK");
            map.put("item", vol.getName());
            result.add(map);
            return ResponseEntity.noContent().build();
        }
    }

    @CrossOrigin(origins = "*")
    @PostMapping("/delete/{id}")
    @ResponseBody
    public void delete(@PathVariable Long id) throws ParseException {
        Voluntary vol = voluntaryRepository.findVoluntaryById(id);
        if(vol == null) {
            System.out.println("Voluntary does not exist!!");
            return ;
        }
        else {
            String erasedUser = vol.getName();
            voluntaryRepository.deleteById(id);
            return ;
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
            if (vol.getAsignated()== true){
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
                    if (asignacion.getStatus()==2){     //Ya fue rechazada
                        map.put("status", "404");
                        map.put("message", "Task  already rejected!.");
                        map.put("item", "");
                        result.add(map);
                        return result;
                    }
                    else if(asignacion.getStatus()==1){    //Ya fue aceptada
                        map.put("status", "404");
                        map.put("message", "Task  already accepted!.");
                        map.put("item", "");
                        result.add(map);
                        return result;
                    }
                    else if (asignacion.getStatus()==0){    // Asignación por aceptar
                        vol.setAsignated(true);
                        voluntaryRepository.save(vol);
                        asignacion.setStatus(1);
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
            if (vol.getAsignated()== true){
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
                    if (asignacion.getStatus()==2){     //Ya fue rechazada
                        map.put("status", "404");
                        map.put("message", "Task  already rejected!.");
                        map.put("item", "");
                        result.add(map);
                        return result;
                    }
                    else if(asignacion.getStatus()==1){    //Ya fue aceptada
                        map.put("status", "404");
                        map.put("message", "Task  already accepted!.");
                        map.put("item", "");
                        result.add(map);
                        return result;
                    }
                    else if (asignacion.getStatus()==0){    // Asignación por aceptar
                        asignacion.setStatus(2);
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
            if (vol.getAsignated()== false){
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
                    if (asignacion.getStatus()==2){     //Ya fue rechazada
                        map.put("status", "404");
                        map.put("message", "Task  already rejected!.");
                        map.put("item", "");
                        result.add(map);
                        return result;
                    }
                    else if(asignacion.getStatus()==1){    //Ya fue aceptada
                        asignacion.setStatus(3);
                        voluntaryTaskRepository.save(asignacion);
                        vol.setAsignated(false);
                        voluntaryRepository.save(vol);
                        task.setStatus(1);
                        taskRepository.save(task);
                        System.out.println(jsonData);
                        map.put("status", "201");
                        map.put("message", "OK, Task finished!!");
                        result.add(map);
                        return result;
                    }
                    else if (asignacion.getStatus()==0){    // Asignación por aceptar
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

    @GetMapping(value = "/distanciaVoluntarios/{id}")
    @ResponseBody
    public double getDistance(@PathVariable Long id) {
        Voluntary user = voluntaryRepository.findVoluntaryById(id);
        String lat = user.getLatitude();
        String lon = user.getLongitude();

        double lat1 = Double.parseDouble(lat);
        double lon1 = Double.parseDouble(lon);

        double lat2 = 52.85768;
        double lon2 = 14.39293;
        double theta = lon1 - lon2;
        double dist = Math.sin(Math.toRadians(lat1)) * Math.sin(Math.toRadians(lat2)) + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2)) * Math.cos(Math.toRadians(theta));
        dist = Math.acos(dist);
        dist = Math.toDegrees(dist);
        dist = dist * 60 * 1.1515;
        dist = dist * 1.609344; //La entrega en kilometros

        return dist;
    }

    @GetMapping(value = "/voluntariosDisponibles/{radio}/{id_emergencia}")
    @ResponseBody
    public List<Voluntary> getDisponibles(@PathVariable double radio, @PathVariable Long id_emergencia) {
        //Recibo el radio en kilometros

        List<Voluntary> voluntarios = voluntaryRepository.findAll();
        Emergency emergency = emergencyRepository.findEmergencyById(id_emergencia);
        List<Voluntary> voluntarios_disponibles = new ArrayList<>();

        for (int i = 0; i < voluntarios.size(); i++){

            String lat = voluntarios.get(i).getLatitude();
            String lon = voluntarios.get(i).getLongitude();

            String latE = emergency.getLatitude();
            String lonE = emergency.getLongitude();

            GeometryFactory geometryFactory1 = new GeometryFactory();
            Coordinate coordinateVoluntario = new Coordinate();
            double lat1 = Double.parseDouble(lat);
            double lon1 = Double.parseDouble(lon);
            coordinateVoluntario.x = lat1;
            coordinateVoluntario.y = lon1;
            Point locationVoluntario = geometryFactory1.createPoint(coordinateVoluntario);

            GeometryFactory geometryFactory2 = new GeometryFactory();
            Coordinate coordinateEmergencia = new Coordinate();
            double lat2 = Double.parseDouble(latE);
            double lon2 = Double.parseDouble(lonE);
            coordinateEmergencia.x = lat2;
            coordinateEmergencia.y = lon2;
            Point locationEmergencia = geometryFactory2.createPoint(coordinateEmergencia);

            double radio_grados = DistanceUtils.dist2Degrees(radio ,DistanceUtils.EARTH_MEAN_RADIUS_KM);

            if (isWithinDistance(locationEmergencia,locationVoluntario,radio_grados)){
                voluntarios_disponibles.add(voluntarios.get(i));
            }
        }

        return voluntarios_disponibles;
    }

    @GetMapping("/topDistance/{n}/{iduser}")
    @ResponseBody
    public List<Voluntary> getTopDVoluntaries(@PathVariable int n, @PathVariable Long iduser){
        Long id = iduser;
        Emergency emergency = emergencyRepository.findEmergencyById(id);
        List<Voluntary> vols = getAllVolunteers();
        List<Voluntary> top= new ArrayList<>();
        Voluntary aux = new Voluntary();
        Double dist=-1.0;
        HashMap<String, String> map = new HashMap<>();
        List<HashMap<String, String>> result = new ArrayList<HashMap<String, String>>();
        map.put("n = ", String.valueOf(n));



/*
        map.put("Emergency = ", emergency.getDescription());
        map.put("Point A = ", String.valueOf( vols.get(0).getLocation().getX()));
        map.put("Point B = ", String.valueOf(vols.get(1).getLocation().getX()));
        dist = vols.get(0).getLocation().distance( vols.get(1).getLocation());
        map.put("distancia (distance) = ", String.valueOf(dist));
        map.put("distancia (meters) = ", String.valueOf(DistanceUtils.degrees2Dist(dist ,DistanceUtils.EARTH_MEAN_RADIUS_KM)));

        result.add(map);
        return result;
*/

        aux = vols.get(0);
        int j, indice=0;
        for(int i = 0; i < n; i = i + 1){
            dist= emergency.getLocation().distance(vols.get(0).getLocation());
            j=0;
            indice=0;
            for (Voluntary voluntary: vols){
                if (dist>emergency.getLocation().distance(voluntary.getLocation())){
                    dist= emergency.getLocation().distance(voluntary.getLocation());
                    aux = voluntary;
                    indice=j;
                }
                j++;
            }
            //map.put("name = ", aux.getName()+" i = "+String.valueOf(i));
            System.out.println("name = "+ aux.getName()+" i = "+String.valueOf(i));
            top.add(aux);
            vols.remove(indice);
        }
        //result.add(map);
        //return result;
        return top;
    }

}