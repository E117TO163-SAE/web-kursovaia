package com.example.farm.controller;

import com.example.farm.DTO.AnimalDTO;
import com.example.farm.entity.*;
import com.example.farm.repository.AnimalRepository;
import com.example.farm.repository.PlaceRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
public class AnimalControllerTest {
    @Autowired
    private AnimalController animalController;
    @Autowired
    private AnimalRepository animalRepository;
    @Autowired
    private PlaceRepository placeRepository;



    private Long animal1_Id;
    private Place placeZagon;


    @BeforeEach
    public void before(){
        placeZagon = new Place();
        placeZagon.setPlaceName(PlaceName.ZAGON);
        placeZagon.setTemperature(22L);
        placeZagon.setHumidity(65L);
        placeZagon = placeRepository.save(placeZagon);


        Animal animal1 = new Animal();
        animal1.setClassion(Classification.HEREFORD);
        animal1.setGender(Gender.MALE);
        animal1.setBirthday(LocalDateTime.of(2023, 10, 12, 13, 30));
        animal1.setHealthStatus(HealthStatus.HEALTHY);
        animal1.setPlace(placeZagon);
        animalRepository.save(animal1);
        animal1_Id = animal1.getId();

        Animal animal2 = new Animal();
        animal2.setClassion(Classification.LIMOUSIN);
        animal2.setGender(Gender.MALE);
        animal2.setBirthday(LocalDateTime.of(2026, 01, 06, 17, 14));
        animal2.setHealthStatus(HealthStatus.HEALTHY);
        animal2.setPlace(placeZagon);
        animalRepository.save(animal2);

        Animal animal3 = new Animal();
        animal3.setClassion(Classification.ABERDEEN_ANGUS);
        animal3.setGender(Gender.FEMALE);
        animal3.setBirthday(LocalDateTime.of(2023, 06, 13, 13, 30));
        animal3.setHealthStatus(HealthStatus.SICK);
        animal3.setPlace(placeZagon);
        animalRepository.save(animal3);

        Animal animal4 = new Animal();
        animal4.setClassion(Classification.ABERDEEN_ANGUS);
        animal4.setGender(Gender.FEMALE);
        animal4.setBirthday(LocalDateTime.of(2023, 06, 13, 13, 30));
        animal4.setHealthStatus(HealthStatus.SICK);
        animal4.setPlace(placeZagon);
        animalRepository.save(animal4);
    }

    @AfterEach
    public void after() {
        animalRepository.deleteAllInBatch();
        placeRepository.deleteAllInBatch();
    }

    @Test
    void testFindAll() {
        List<AnimalDTO> animals = animalController.findAll();

        assertEquals(4, animals.size());
    }
    @Test
    void testCreate() {
        AnimalDTO newAnimal = new AnimalDTO();
        //newAnimal.setId(88L);
        newAnimal.setClassion(Classification.ABERDEEN_ANGUS);
        newAnimal.setGender(Gender.FEMALE);
        newAnimal.setBirthday(LocalDateTime.now());
        newAnimal.setHealthStatus(HealthStatus.HEALTHY);
        newAnimal.setPlaceId(placeZagon.getPlaceId());

        AnimalDTO created = animalController.create(newAnimal).getBody();

        //ResponseEntity<AnimalDTO> response = animalController.create(newAnimal);

        //assertEquals(201, response.getStatusCode().value());
        //AnimalDTO created = response.getBody();

        assertNotNull(created);
        assertNotNull(created.getId());
        //assertEquals(88L, created.getId());
        assertEquals(Classification.ABERDEEN_ANGUS, created.getClassion());
        assertEquals(Gender.FEMALE, created.getGender());
        assertEquals(HealthStatus.HEALTHY, created.getHealthStatus());
        assertEquals(placeZagon.getPlaceId(), created.getPlaceId());
    }
    @Test
    void testUpdate() {
        AnimalDTO updateData = new AnimalDTO();
        updateData.setClassion(Classification.HEREFORD);
        updateData.setGender(Gender.FEMALE);
        updateData.setBirthday(LocalDateTime.of(2022, 1, 1, 12, 0));
        updateData.setHealthStatus(HealthStatus.SICK);
        updateData.setPlaceId(placeZagon.getPlaceId());

        ResponseEntity<Void> response = animalController.update(animal1_Id, updateData);

        assertEquals(200, response.getStatusCode().value());

        AnimalDTO updated = animalController.findById(animal1_Id).getBody();

        assertEquals(Gender.FEMALE, updated.getGender());
        assertEquals(HealthStatus.SICK, updated.getHealthStatus());
    }





}
