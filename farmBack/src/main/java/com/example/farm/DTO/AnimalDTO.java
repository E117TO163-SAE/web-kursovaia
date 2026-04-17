package com.example.farm.DTO;

import com.example.farm.entity.Classification;
import com.example.farm.entity.Gender;
import com.example.farm.entity.HealthStatus;
import com.example.farm.entity.PlaceName;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.time.LocalDateTime;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
public class AnimalDTO {
    private Long id;
    private Classification classion;
    private Gender gender;
    private LocalDateTime birthday;
    private HealthStatus healthStatus;
    private Long placeId;
    private PlaceName placeName; //А может и не надо... надо подумать.
}
