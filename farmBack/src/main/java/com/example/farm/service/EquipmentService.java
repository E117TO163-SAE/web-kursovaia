package com.example.farm.service;

import com.example.farm.DTO.EquipmentDTO;
import com.example.farm.DTO.MappingUtils;
import com.example.farm.repository.EquipmentRepository;
import jakarta.persistence.ManyToMany;
import org.apache.catalina.mapper.Mapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EquipmentService {
    private MappingUtils mappingUtils;
    private EquipmentRepository equipmentRepository;
    public EquipmentService(MappingUtils mappingUtils, EquipmentRepository equipmentRepository){
        this.mappingUtils = mappingUtils;
        this.equipmentRepository = equipmentRepository;
    }

    public List<EquipmentDTO> findAll(){
        return equipmentRepository.findAll().stream().map(mappingUtils::equipmentToDto).toList();
    }

    public Optional<EquipmentDTO> findById(Long id){
        return equipmentRepository.findById(id).map(mappingUtils::equipmentToDto);
    }

    public EquipmentDTO create(EquipmentDTO equipmentDTO){
        return mappingUtils.equipmentToDto(equipmentRepository.save(mappingUtils.equipmentToEntity(equipmentDTO)));
    }

    public void update(Long id,EquipmentDTO newParam){
        EquipmentDTO equip = findById(id).orElseThrow();

        equip.setEquipmentType(newParam.getEquipmentType());
        equip.setEquipmentName(newParam.getEquipmentName());
        equip.setEquipmentStatus(newParam.getEquipmentStatus());

        mappingUtils.equipmentToDto(equipmentRepository.save(mappingUtils.equipmentToEntity(equip)));
    }

    public void delete(Long id){
        equipmentRepository.deleteById(id);
    }
}
