package com.example.farm.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
@Entity
@Table(name = "worker")
public class Worker {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "worker_id")
    private Long id;

    @Column(name = "login")
    private String login;

    @Column(name = "surname")
    private String surname;

    @Column(name = "firstname")
    private String firstname;

    @Column(name = "patronymic")
    private String patronymic;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "worker_role", joinColumns = @JoinColumn(name = "worker_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private List<Role> role;

    @OneToMany(mappedBy = "worker", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Task> tasks;

    @Column(name = "password")
    private String password;

}
