package com.example.farm.service;

import com.example.farm.DTO.EquipmentDTO;
import com.example.farm.DTO.MappingUtils;
import com.example.farm.DTO.PlaceDTO;
import com.example.farm.entity.Place;
import com.example.farm.repository.PlaceRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PlaceService {

    private MappingUtils mappingUtils;
    private PlaceRepository placeRepository;
    public PlaceService(MappingUtils mappingUtils, PlaceRepository placeRepository){
        this.mappingUtils = mappingUtils;
        this.placeRepository = placeRepository;
    }

    public List<PlaceDTO> findAll(){
        return placeRepository.findAll().stream().map(mappingUtils::placeToDto).toList();
    }

    public Optional<PlaceDTO> findById(Long id){
        return placeRepository.findById(id).map(mappingUtils::placeToDto);
    }

    public PlaceDTO create(PlaceDTO placeDTO){
        return mappingUtils.placeToDto(placeRepository.save(mappingUtils.placeToEntity(placeDTO)));
    }

    public void update(Long id, PlaceDTO newParam){
        PlaceDTO place = findById(id).orElseThrow();

        place.setTemperature(newParam.getTemperature());
        place.setHumidity(newParam.getHumidity());


        mappingUtils.placeToDto(placeRepository.save(mappingUtils.placeToEntity(place)));
    }

    public void delete(Long id){
        placeRepository.deleteById(id);
    }
}
