package com.example.demo.repositories;

import com.example.demo.models.City;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CityRepository extends JpaRepository<City, Long> {

    City findCityByName(String name);
    City findCityById(Long id);
}
