package com.example.farm.service;

import com.example.farm.DTO.AnimalDTO;
import com.example.farm.DTO.MappingUtils;
import com.example.farm.entity.Animal;
import com.example.farm.entity.Worker;
import com.example.farm.repository.AnimalRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AnimalService {
    private MappingUtils mappingUtils;
    private AnimalRepository animalRepository;

    public AnimalService(MappingUtils mappingUtils, AnimalRepository animalRepository){
        this.mappingUtils = mappingUtils;
        this.animalRepository = animalRepository;
    }

    public List<AnimalDTO> findAll(){
        return animalRepository.findAll().stream().map(mappingUtils::animalToDto).toList();
    }

    public Optional<AnimalDTO> findById(Long id){
        return animalRepository.findById(id).map(mappingUtils::animalToDto);
    }

    public AnimalDTO create(AnimalDTO animalDTO){
        Animal animal = mappingUtils.animalToEntity(animalDTO);
        animal.setId(null);
        return mappingUtils.animalToDto(animalRepository.save(animal));
    }

    public  void update(Long id, AnimalDTO newParam){
        AnimalDTO animal = findById(id).orElseThrow();

        animal.setClassion(newParam.getClassion());
        animal.setGender(newParam.getGender());
        animal.setBirthday(newParam.getBirthday());
        animal.setHealthStatus(newParam.getHealthStatus());
        animal.setPlaceId(newParam.getPlaceId());

        mappingUtils.animalToDto(animalRepository.save(mappingUtils.animalToEntity(animal)));
    }

    public void delete(Long id){
        animalRepository.deleteById(id);
    }


}
