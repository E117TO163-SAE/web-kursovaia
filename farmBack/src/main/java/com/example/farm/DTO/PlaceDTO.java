package com.example.farm.DTO;

import com.example.farm.entity.PlaceName;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;


@Data
public class PlaceDTO {
    private Long id;
    private PlaceName placeName;
    private Long temperature;
    private Long humidity;
}
