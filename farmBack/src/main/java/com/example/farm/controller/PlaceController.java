package com.example.farm.controller;

import com.example.farm.DTO.PlaceDTO;
import com.example.farm.DTO.TaskDTO;
import com.example.farm.entity.Place;
import com.example.farm.service.PlaceService;
import jdk.jfr.BooleanFlag;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/places")
public class PlaceController {
    private PlaceService placeService;
    public PlaceController(PlaceService placeService){
        this.placeService = placeService;
    }

    @GetMapping
    public List<PlaceDTO> findAll(){
        return placeService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<PlaceDTO> findById(@PathVariable Long id){
        return placeService.findById(id).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> update(@PathVariable Long id ,@RequestBody PlaceDTO newParam){
        if(newParam == null){
            return ResponseEntity.notFound().build();
        }

        placeService.update(id, newParam);

        return ResponseEntity.ok().build();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<PlaceDTO> create(@RequestBody PlaceDTO place){
        place = placeService.create(place);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(place.getId())
                .toUri();
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(location);
        return new ResponseEntity<>(place, headers, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id){
        if (placeService.findById(id).isEmpty()) {
            return ResponseEntity.badRequest().build();
        }

        placeService.delete(id);
        return ResponseEntity.ok().build();

    }
}
