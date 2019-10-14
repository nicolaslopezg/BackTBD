package com.example.demo.controllers;

;
import com.example.demo.models.Equipment;
import com.example.demo.models.Voluntary;
import com.example.demo.models.VoluntaryEquipment;
import com.example.demo.repositories.EquipmentRepository;
import com.example.demo.repositories.VoluntaryEquipmentRepository;
import com.example.demo.repositories.VoluntaryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/voluntaryEquipments")
public class VoluntaryEquipmentController {
    @Autowired
    VoluntaryEquipmentRepository voluntaryEquipmentRepository;
    @Autowired
    VoluntaryRepository voluntaryRepository;
    @Autowired
    EquipmentRepository equipmentRepository;

    @RequestMapping(method = RequestMethod.GET)
    @ResponseBody
    public List<VoluntaryEquipment> getAllVoluntaryEquipment() {
        return voluntaryEquipmentRepository.findAll();
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @ResponseBody
    public VoluntaryEquipment getVoluntaryEquipmentById(@PathVariable Long id) {
        return voluntaryEquipmentRepository.findVoluntaryEquipmentById(id);
    }

    @RequestMapping(value = "/voluntary/{id}", method = RequestMethod.GET)
    @ResponseBody
    public List<VoluntaryEquipment> getRegistroServicioByIdRegistro(@PathVariable Long id) {
        Voluntary vol = voluntaryRepository.findVoluntaryById(id);
        return voluntaryEquipmentRepository.findVoluntaryEquipmentByVoluntary(vol);
    }

    @PostMapping("/create")
    @ResponseBody
    public List<HashMap<String, String>> create(@RequestBody Map<String, Object> jsonData) throws ParseException {
        List<HashMap<String, String>> result = new ArrayList<HashMap<String, String>>();
        HashMap<String, String> map = new HashMap<>();
        Long idVoluntary = Long.parseLong(jsonData.get("idVoluntary").toString());
        Long idEquipment = Long.parseLong(jsonData.get("idEquipment").toString());
        Voluntary vol = voluntaryRepository.findVoluntaryById(idVoluntary);
        Equipment equip = equipmentRepository.findEquipmentById(idEquipment);
        if(vol != null){
            if(equip != null){
                voluntaryEquipmentRepository.save(new VoluntaryEquipment(vol,equip));
                map.put("status", "201");
                map.put("message", "VoluntaryEquipment added");
                result.add(map);
                return result;
            } else {
                map.put("status", "401");
                map.put("message", "Equipment does not exist!.");
                result.add(map);
                return result;
            }
        } else {
            map.put("status", "401");
            map.put("message", "Voluntary does not exist!.");
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
        VoluntaryEquipment voleq = voluntaryEquipmentRepository.findVoluntaryEquipmentById(id);


        Voluntary vol = voleq.getVoluntary();
        Equipment equip = voleq.getEquipment();

        if(vol == null) {
            map.put("status", "404");
            map.put("message", "Voluntary does not exist!.");
            map.put("item", "");
            result.add(map);
            return result;
        }
        else if(equip == null) {
            map.put("status", "404");
            map.put("message", "Equipment does not exist!.");
            map.put("item", "");
            result.add(map);
            return result;
        }
        else {
            voluntaryEquipmentRepository.deleteById(id);
            map.put("status", "200");
            map.put("message", "OK, voluntaryEquipment erased!.");
            map.put("item", id.toString());
            result.add(map);
            return result;
        }
    }
}