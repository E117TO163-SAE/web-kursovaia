package com.example.farm.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "animal")
public class Animal {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "animal_id")
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "type")
    private Classification classion;

    @Enumerated(EnumType.STRING)
    @Column(name = "gender")
    private Gender gender;

    @Column(name = "birthday")
    private LocalDateTime birthday;

    @Enumerated(EnumType.STRING)
    @Column(name = "health_status")
    private HealthStatus healthStatus;

    @ManyToOne
    @JoinColumn(name = "place")
    private Place place;



}
