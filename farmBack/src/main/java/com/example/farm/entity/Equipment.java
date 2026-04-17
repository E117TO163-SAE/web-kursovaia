package com.example.farm.entity;

import com.example.farm.DTO.EquipmentDTO;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "equipment")
public class Equipment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "equipment_id")
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "equipment_type")
    private EquipmentType equipmentType;

    @Column(name = "equipment_name")
    private String equipmentName;

    @Column(name = "equipment_status")
    @Enumerated(EnumType.STRING)
    private EquipmentStatus equipmentStatus;


    //private Long numberEquipment;
    /*private List<Task> tasks;*/
}
