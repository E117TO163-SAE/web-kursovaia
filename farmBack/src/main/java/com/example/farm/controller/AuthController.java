package com.example.farm.controller;

import com.example.farm.DTO.WorkerDTO;
import com.example.farm.entity.Role;
import com.example.farm.entity.RoleName;
import com.example.farm.repository.WorkerRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/api/auth")
public class AuthController {
    private WorkerRepository workerRepository;
    public AuthController(WorkerRepository workerRepository){
        this.workerRepository = workerRepository;
    }

    @GetMapping("/me")
    public ResponseEntity<WorkerDTO> getLoginAndPass(@AuthenticationPrincipal UserDetails userDetails) {
        WorkerDTO workerDto = new WorkerDTO();

        List<Role> roles = userDetails.getAuthorities().stream()
                .map(role -> new Role(RoleName.valueOf(role.getAuthority())) )
                .collect(Collectors.toList());

        workerDto.setId(workerRepository.findByLogin(userDetails.getUsername()).get().getId());
        workerDto.setLogin(userDetails.getUsername());
        workerDto.setRoles(roles);
        return ResponseEntity.ok(workerDto);

    }
}
