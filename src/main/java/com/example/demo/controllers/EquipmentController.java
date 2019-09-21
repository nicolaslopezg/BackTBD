package com.example.demo.controllers;


import com.example.demo.models.Equipment;
import com.example.demo.repositories.EquipmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.*;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/equipments")
public class EquipmentController {
    @Autowired
    EquipmentRepository equipmentRepository;

    private Random randomGenerator = new Random();

    @GetMapping
    @ResponseBody
    public List<Equipment> getAllEquipments() {
        return equipmentRepository.findAll();
    }

    @GetMapping(value = "/{id}")
    @ResponseBody
    public String getEquipmentById(@PathVariable Long id) {
        Equipment user = equipmentRepository.findEquipmentByIdEquipment(id);
        return user.getNombreEquipment();
    }

    @GetMapping(value = "/tipo/{tipo}")
    @ResponseBody
    public List<Equipment> getUserByRut(@PathVariable Integer rut){
        return equipmentRepository.findEquipmentByTipo(rut);
    }

    @PostMapping("/create")
    @ResponseBody
    public List<HashMap<String, String>> create(@RequestBody Map<String, Object> jsonData) throws ParseException {
        List<HashMap<String, String>> result = new ArrayList<HashMap<String, String>>();
        HashMap<String, String> map = new HashMap<>();
        long codigo;
        Equipment equip;
        do {
            codigo = randomGenerator.nextInt(100) + 1;
            equip = equipmentRepository.findEquipmentByCodigoEquipment(codigo);
        }while(equip!=null);

        if(equip == null) {
            equipmentRepository.save(new Equipment(codigo,
                    Integer.parseInt(jsonData.get("tipo").toString()),
                    jsonData.get("nombre").toString(),
                    jsonData.get("certificacion").toString(),
                    jsonData.get("detalle").toString()));
            System.out.println(jsonData);
            map.put("status", "201");
            map.put("message", "OK");
            result.add(map);
            return result;
        }
        else {
            map.put("status", "401");
            map.put("message", "Equipment with this code already exist.");
            map.put("item", equip.getNombreEquipment());
            result.add(map);
            return result;
        }
    }

    @PostMapping("/update/{codigoEquipment}")
    @ResponseBody
    public List<HashMap<String, String>> update(@PathVariable Long codigoEquipment, @RequestBody Map<String, Object> jsonData) throws ParseException {
        List<HashMap<String, String>> result = new ArrayList<HashMap<String, String>>();
        HashMap<String, String> map = new HashMap<>();
        Equipment usuario = equipmentRepository.findEquipmentByCodigoEquipment(codigoEquipment);
        if(usuario == null) {
            map.put("status", "404");
            map.put("message", "Equipment does not exist!.");
            map.put("item", "");
            result.add(map);
            return result;
        }
        else {
            usuario.setTipo(Integer.parseInt(jsonData.get("tipo").toString()));
            usuario.setNombreEquipment(jsonData.get("nombre").toString());
            usuario.setCertificacion(jsonData.get("certificacion").toString());
            usuario.setDetalle(jsonData.get("detalle").toString());
            equipmentRepository.save(usuario);
            map.put("status", "200");
            map.put("message", "OK");
            map.put("item", usuario.getNombreEquipment());
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
        Equipment usuario = equipmentRepository.findEquipmentByIdEquipment(id);
        if(usuario == null) {
            map.put("status", "404");
            map.put("message", "Equipment does not exist!.");
            map.put("item", "");
            result.add(map);
            return result;
        }
        else {
            String erasedUser = usuario.getNombreEquipment();
            equipmentRepository.deleteById(id);
            map.put("status", "200");
            map.put("message", "OK, equipment erased!.");
            map.put("item", erasedUser);
            result.add(map);
            return result;
        }
    }
}
