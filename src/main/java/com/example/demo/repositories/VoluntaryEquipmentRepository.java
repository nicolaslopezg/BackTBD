package com.example.demo.repositories;

import com.example.demo.models.Voluntary;
import com.example.demo.models.VoluntaryEquipment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
@Transactional
public interface VoluntaryEquipmentRepository extends JpaRepository<VoluntaryEquipment, Long> {
    VoluntaryEquipment findVoluntaryEquipmentById(Long id);
    List<VoluntaryEquipment> findVoluntaryEquipmentByVoluntary(Voluntary voluntary);
}
