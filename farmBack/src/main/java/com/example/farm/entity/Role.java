package com.example.farm.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "role")
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "role_id")
    private Long id;

    @Column(name = "roleName")
    @Enumerated(EnumType.STRING)
    private RoleName roleName;

    //private List<Worker> worker;

    public Role(long id, RoleName roleName) {
        this.id = id;
        this.roleName = roleName;
    }

    public Role( RoleName roleName) {

        this.roleName = roleName;
    }
}
