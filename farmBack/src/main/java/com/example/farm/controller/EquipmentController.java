package com.example.farm.controller;

import com.example.farm.DTO.EquipmentDTO;
import com.example.farm.DTO.PlaceDTO;
import com.example.farm.DTO.TaskDTO;
import com.example.farm.entity.EquipmentStatus;
import com.example.farm.entity.EquipmentType;
import com.example.farm.repository.EquipmentRepository;
import com.example.farm.service.EquipmentService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/equipment")
public class EquipmentController {
    private EquipmentService equipmentService;
    public EquipmentController(EquipmentService equipmentService){
        this.equipmentService = equipmentService;
    }

    @GetMapping
    public List<EquipmentDTO> findAll(){
        return equipmentService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<EquipmentDTO> findById(@PathVariable Long id){
        return equipmentService.findById(id).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<EquipmentDTO> create(@RequestBody EquipmentDTO equipment){
        equipment = equipmentService.create(equipment);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(equipment.getId())
                .toUri();
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(location);
        return new ResponseEntity<>(equipment, headers, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> update(@PathVariable Long id, @RequestBody EquipmentDTO newParam){
        if(newParam == null){
            return ResponseEntity.notFound().build();
        }

        equipmentService.update(id, newParam);

        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id){
        Optional<EquipmentDTO> equipmentDTO = equipmentService.findById(id);

        if (equipmentDTO.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }

        equipmentService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
