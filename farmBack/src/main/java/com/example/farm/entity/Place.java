package com.example.farm.entity;

import com.example.farm.DTO.PlaceDTO;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
@Entity
@Table(name = "place")
public class Place {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "place_id")
    private Long placeId;

    @Column(name = "placeName")
    @Enumerated(EnumType.STRING)
    private PlaceName placeName;

    @Column(name = "temperature")
    private Long temperature;

    @Column(name = "humidity")
    private Long humidity;

    @OneToMany(mappedBy = "place")
    private List<Animal> animals;

    //private LocalDateTime startDate;
    //private LocalDateTime endData;
}
