package com.example.farm.repository;

import com.example.farm.entity.Animal;
import com.example.farm.entity.Classification;
import com.example.farm.entity.HealthStatus;
import com.example.farm.entity.Place;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Repository
public interface AnimalRepository  extends JpaRepository<Animal, Long> {


    List<Animal> findByPlace(Place place);
    List<Animal> findByHealthStatus(HealthStatus healthStatus);
    List<Animal> findByClassion(Classification classion);
    //Long countByPlaceId(Long placeId);
    //Long countByClassionAndPlaceId(Classification classion, Long placeId);

    @Query("SELECT a FROM Animal a WHERE YEAR(CURRENT_DATE) - YEAR(a.birthday) >= :age AND a.classion = :classion")
    List<Animal> findByAgeAndClassification(@Param("age") Long age, @Param("classion") Classification classion);

}
