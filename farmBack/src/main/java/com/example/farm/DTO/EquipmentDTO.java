package com.example.farm.DTO;

import com.example.farm.entity.EquipmentStatus;
import com.example.farm.entity.EquipmentType;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
public class EquipmentDTO {
    private Long id;
    private EquipmentType equipmentType;
    private String equipmentName;
    private EquipmentStatus equipmentStatus;
}