package com.example.demo.repositories;

import com.example.demo.models.Voluntary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
@Transactional
public interface VoluntaryRepository extends JpaRepository<Voluntary, Long> {

    Voluntary findVoluntaryByIdVoluntary(Long idVoluntary);
    Voluntary findVoluntaryByRut(int rut);
    List<Voluntary> findVoluntaryByAsignado(Boolean asignado);

}