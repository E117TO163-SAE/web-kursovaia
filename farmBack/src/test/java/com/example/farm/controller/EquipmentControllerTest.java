package com.example.farm.controller;

import com.example.farm.DTO.EquipmentDTO;
import com.example.farm.controller.EquipmentController;
import com.example.farm.entity.Equipment;
import com.example.farm.entity.EquipmentStatus;
import com.example.farm.entity.EquipmentType;
import com.example.farm.repository.EquipmentRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class EquipmentControllerTest {
    @Autowired
    private EquipmentController equipmentController;

    @Autowired
    private EquipmentRepository equipmentRepository;


    private Long id;

    @BeforeEach
    public void before() {
        Equipment equip = new Equipment();
        equip.setEquipmentType(EquipmentType.TRUCK);
        equip.setEquipmentName("Name1");
        equip.setEquipmentStatus(EquipmentStatus.BUSY);
        id = equipmentRepository.save(equip).getId();

        Equipment equip2 = new Equipment();
        equip2.setEquipmentType(EquipmentType.TRUCK);
        equip2.setEquipmentName("Name2");
        equip2.setEquipmentStatus(EquipmentStatus.BUSY);
        equipmentRepository.save(equip2).getId();

    }
    @AfterEach
    public void after(){
        equipmentRepository.deleteAllInBatch();
    }

    @Test
    void testCreate(){
        EquipmentDTO equip = new EquipmentDTO();
        equip.setEquipmentType(EquipmentType.TRACTOR);
        equip.setEquipmentName("Name2");
        equip.setEquipmentStatus(EquipmentStatus.FREE);

        EquipmentDTO equipmentDTO = equipmentController.create(equip).getBody();

        assertEquals("Name2", equipmentDTO.getEquipmentName());
        assertEquals(EquipmentType.TRACTOR, equipmentDTO.getEquipmentType());
        assertEquals(EquipmentStatus.FREE, equipmentDTO.getEquipmentStatus());
    }

    @Test
    void testFindAll(){
        List<EquipmentDTO> equipmentDTO = equipmentController.findAll();

        assertEquals(2, equipmentDTO.size());
    }


    @Test
    void testFindById(){
        EquipmentDTO equipmentDTO = equipmentController.findById(id).getBody();


        assertEquals("Name1", equipmentDTO.getEquipmentName());
        assertEquals(EquipmentType.TRUCK, equipmentDTO.getEquipmentType());
        assertEquals(EquipmentStatus.BUSY, equipmentDTO.getEquipmentStatus());
    }

    @Test
    void testUpdate(){
        EquipmentDTO equip = new EquipmentDTO();
        equip.setEquipmentType(EquipmentType.DRILL);
        equip.setEquipmentName("Name33update");
        equip.setEquipmentStatus(EquipmentStatus.REPAIR);

        equipmentController.update(id, equip);

        EquipmentDTO equipmentDTO = equipmentController.findById(id).getBody();

        assertEquals("Name33update", equipmentDTO.getEquipmentName());
        assertEquals(EquipmentType.DRILL, equipmentDTO.getEquipmentType());
        assertEquals(EquipmentStatus.REPAIR, equipmentDTO.getEquipmentStatus());
    }

    @Test
    void testUpdateNoParam(){
        org.springframework.http.ResponseEntity<Void> nf = equipmentController.update(id, null);

        assertEquals("404 NOT_FOUND", nf.getStatusCode().toString());


    }
}
