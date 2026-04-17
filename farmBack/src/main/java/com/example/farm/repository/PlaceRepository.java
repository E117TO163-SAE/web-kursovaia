package com.example.farm.repository;

import com.example.farm.entity.Place;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PlaceRepository extends JpaRepository<Place, Long> {

    // Найти место по названию
    List<Place> findByPlaceNameContainingIgnoreCase(String placeName);

    // Найти места с температурой выше указанной
    List<Place> findByTemperatureGreaterThan(Long temperature);

    // Найти места с температурой ниже указанной
    List<Place> findByTemperatureLessThan(Long temperature);

    // Найти места с влажностью выше указанной
    List<Place> findByHumidityGreaterThan(Long humidity);

    @Query("SELECT p FROM Place p WHERE p.temperature BETWEEN 17 AND 25 AND p.humidity BETWEEN 50 AND 70")
    List<Place> findPlacesWithNormalConditions();

    @Query("SELECT p.placeName, COUNT(a) FROM Place p LEFT JOIN p.animals a GROUP BY p.id")
    List<Object[]> countAnimalsInAllPlace();

    Long countAnimalsByPlaceId(Long id);

    // Сколько животных каждого типа в конкретном месте
    /*@Query("SELECT a.classion, COUNT(a) FROM Animal a WHERE a.place.id = :id GROUP BY a.classion")
    List<Object[]> countAnimalsByTypeInPlace(@Param("placeId") Long id);*/

    @Query("SELECT p FROM Place p WHERE p.animals IS EMPTY")
    List<Place> findEmptyPlaces();
}