package com.example.demo.Repositories;

import com.example.demo.Models.Equipment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EquipmentRepository extends JpaRepository<Equipment, Long> {

    Equipment  findEquipmentByIdEquipment(Long idEquipment);
    List<Equipment> findEquipmentByTipo(int tipo);
    Equipment  findEquipmentByCodigoEquipment(Long codigoEquipment);

}
