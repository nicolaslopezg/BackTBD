package com.example.demo.controllers;

import com.example.demo.models.Dimension;
import com.example.demo.models.VoluntaryDimension;
import com.example.demo.models.Voluntary;
import com.example.demo.repositories.VoluntaryDimensionRepository;
import com.example.demo.repositories.VoluntaryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/")
@CrossOrigin(origins = "*")

public class VoluntaryDimensionController {

    @Autowired
    VoluntaryDimensionRepository repository;

    @Autowired
    VoluntaryRepository voluntaryRepository;

    @GetMapping("/voluntary_dimensions")
    public List<VoluntaryDimension> getAll(){return repository.findAll(); }

    //No parte de la id 1.
    @PostMapping("/voluntary_dimensions")
    VoluntaryDimension insertVoluntaryDimension(@RequestBody VoluntaryDimension newVoluntaryDimension){ return repository.save(newVoluntaryDimension); }

    @GetMapping("/voluntary_dimension_id/{id}")
    VoluntaryDimension getVoluntaryDimensionId(@PathVariable Long id){
        return repository.findVDById(id);
    }

    @PostMapping("/voluntary_dimensions/topByDimension")
    @ResponseBody
    public List<HashMap<String, String>> topByDimension(@RequestBody Map<String, Object> jsonData) {
        List<HashMap<String, String>> result = new ArrayList<HashMap<String, String>>();
        HashMap<String, String> map = new HashMap<>();
        int fuerza = Integer.parseInt(jsonData.get("fuerza").toString());
        int destreza = Integer.parseInt(jsonData.get("destreza").toString());
        int liderazgo = Integer.parseInt(jsonData.get("liderazgo").toString());
        int motivacion = Integer.parseInt(jsonData.get("motivacion").toString());
        int conocimiento = Integer.parseInt(jsonData.get("conocimiento").toString());
        //Task task = repository.findTaskById(idTask);
        List<Voluntary> voluntarios = voluntaryRepository.findVoluntaryByAsignado(true);

        //Declaramos variables a ocupar
        List<VoluntaryDimension> dimensiones;
        List<Long> top = new ArrayList<Long>();;
        int score, max=0;
        Long idMax = null;

        //La idea es generar el top a partir de este for de mayor a menor
        for (int i =0; i <= voluntarios.size() ; i = i + 1){
            //Buscamos en toda la lista el score mas alto de los voluntarios
            for (Voluntary voluntario: voluntarios) {
                //Revisamos si el voluntario ya ha sido agregado a la lista auxiliar
                if (top.contains(voluntario.getIdVoluntary())==false){
                        //Se resetea el score
                        score=0;
                        dimensiones = repository.findVoluntaryDimensionByVoluntary(voluntario);
                        //Calculamos el score
                        for (VoluntaryDimension dimension: dimensiones) {
                            if (dimension.getDimension().getName().equals("fuerza")){
                                score = score+fuerza;
                            }
                            if (dimension.getDimension().getName().equals("destreza")){
                                score = score+destreza;
                            }
                            if (dimension.getDimension().getName().equals("liderazgo")){
                                score = score+liderazgo;
                            }
                            if (dimension.getDimension().getName().equals("motivacion")){
                                score = score+motivacion;
                            }
                            if (dimension.getDimension().getName().equals("conocimiento")){
                                score = score+conocimiento;
                            }
                        }
                        //Se compara el score con el maximo score encontrado(hasta el momento), si es mayor se guarda el resultado
                        if (score>max){
                            max= score;
                            idMax = voluntario.getIdVoluntary();
                        }
                }
            }
            //Una vez recorrido todos los voluntarios de la lista se agrega el máximo score a la lista auxiliar
            top.add(idMax);
            //Se agregan los valores del mayor a la lista map (es el json que se manda de vuelta al hacer el post)
            map.put("rut",  Integer.toString(voluntaryRepository.findVoluntaryByIdVoluntary(idMax).getRut()));
            map.put("nombre", voluntaryRepository.findVoluntaryByIdVoluntary(idMax).getNombre());
            map.put("apellido", voluntaryRepository.findVoluntaryByIdVoluntary(idMax).getApellido());
            result.add(map);
            map = new HashMap<>();
            //Reseteamos el máximo para volver hacer el ciclo
            max=0;
        }
        //Retornamos la lista de map
        return result;
    }
}
