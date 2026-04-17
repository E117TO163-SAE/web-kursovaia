package com.example.farm.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Entity
@Table(name = "task")
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "task_id")
    private Long id;

    @Column(name = "title")
    private String title;

    @CreationTimestamp
    @Column(name = "data")
    private LocalDateTime data;

    @Column(name = "deadline")
    private LocalDateTime deadline;

    @ManyToOne
    @JoinColumn(name = "worker")
    private Worker worker;

    @ManyToOne
    @JoinColumn(name = "equipment")
    private Equipment equipment;

    @ManyToMany
    @JoinTable(name = "animal_task", joinColumns = @JoinColumn(name = "task_id"),
            inverseJoinColumns = @JoinColumn(name = "animal_id"))
    private List<Animal> animals;

    @Column(name = "taskStatus")
    @Enumerated(EnumType.STRING)
    private TaskStatus taskStatus;
    //private PlaceOnFarm location;

}
