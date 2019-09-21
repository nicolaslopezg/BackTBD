package com.example.demo.repositories;

import com.example.demo.models.Voluntary;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VoluntaryRepository extends JpaRepository<Voluntary, Long> {

    Voluntary findVoluntaryByIdVoluntary(Long idVoluntary);
    Voluntary findVoluntaryByRut(int rut);

}