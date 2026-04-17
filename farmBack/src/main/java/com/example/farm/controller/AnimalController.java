package com.example.farm.controller;

import com.example.farm.DTO.AnimalDTO;
import com.example.farm.DTO.TaskDTO;
import com.example.farm.service.AnimalService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/animals")
public class AnimalController {
    private AnimalService animalService;
    public AnimalController(AnimalService animalService){
        this.animalService = animalService;
    }



    @GetMapping
    public List<AnimalDTO> findAll(){
        return animalService.findAll();
    }
    @GetMapping("/{id}")
    public ResponseEntity<AnimalDTO> findById(@PathVariable Long id){
        return animalService.findById(id).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<AnimalDTO> create(@RequestBody AnimalDTO animal){
        animal = animalService.create(animal);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(animal.getId())
                .toUri();
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(location);
        return new ResponseEntity<>(animal, headers, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> update(@PathVariable Long id, @RequestBody AnimalDTO newParam){
        if(newParam == null){
            return ResponseEntity.notFound().build();
        }

        animalService.update(id, newParam);

        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id){
        Optional<AnimalDTO> taskOptional = animalService.findById(id);

        if (taskOptional.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }

        animalService.delete(id);
        return ResponseEntity.noContent().build();
    }


}
