package com.example.demo.repositories;

import com.example.demo.models.District;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DistrictRepository  extends JpaRepository<District, Long> {
    District findDistrictByName(String name);
    District findDistrictById(Long id);
}
