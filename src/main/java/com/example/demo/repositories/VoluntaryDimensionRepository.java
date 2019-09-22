package com.example.demo.repositories;

import com.example.demo.models.Dimension;
import com.example.demo.models.Voluntary;
import com.example.demo.models.VoluntaryDimension;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VoluntaryDimensionRepository extends JpaRepository<VoluntaryDimension, Long>{
    // Aún no funcionan.
    VoluntaryDimension findVDById(Long id);
    List<VoluntaryDimension> findVoluntaryDimensionByVoluntary(Voluntary voluntary);
    List<VoluntaryDimension> findVoluntaryDimensionByDimension(Dimension dimension);
    List<VoluntaryDimension> findVoluntaryDimensionByDimensionOrderByQuantityDesc(Dimension dimension);
}