package com.example.farm.DTO;

import com.example.farm.entity.Role;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.util.List;


@Data
public class WorkerDTO {
    private Long id;
    private String login;
    private String surname;
    private String firstname;
    private String patronymic;

    //@JsonIgnore
    private String password;

    private List<Role> roles;
}
