package com.example.demo.repositories;

import com.example.demo.models.Equipment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EquipmentRepository extends JpaRepository<Equipment, Long> {

    Equipment  findEquipmentById(Long id);
    List<Equipment> findEquipmentByType(int type);
    Equipment  findEquipmentByCode(Long code);

}
