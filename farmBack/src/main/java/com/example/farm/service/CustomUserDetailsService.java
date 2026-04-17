package com.example.farm.service;

import com.example.farm.DTO.MappingUtils;
import com.example.farm.DTO.WorkerDTO;
import com.example.farm.entity.Role;
import com.example.farm.entity.RoleName;
import com.example.farm.entity.Worker;
import com.example.farm.repository.WorkerRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CustomUserDetailsService implements UserDetailsService {
    private WorkerRepository workerRepository;
    private MappingUtils mappingUtils;
    private BCryptPasswordEncoder passwordEncoder;

    public CustomUserDetailsService(WorkerRepository workerRepository, MappingUtils mappingUtils,
                                    BCryptPasswordEncoder passwordEncoder){
        this.workerRepository = workerRepository;
        this.mappingUtils = mappingUtils;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Worker worker = workerRepository.findByLogin(username).orElseThrow(()->
                new UsernameNotFoundException("Worker :"+username+" not found"));

        return org.springframework.security.core.userdetails.User.builder()
                .username(worker.getLogin())
                .password(worker.getPassword())
                .authorities((worker.getRole().stream()
                        .map(role -> new SimpleGrantedAuthority(role.getRoleName().name()))
                        .collect(Collectors.toList()))).build();

    }

    public WorkerDTO register(WorkerDTO workerDTO){
        Worker worker = mappingUtils.workerToEntity(workerDTO);
        worker.setId(null);
        worker.setPassword(passwordEncoder.encode(worker.getPassword()));

        if(workerDTO.getLogin().contains("admin")){
            worker.setRole(List.of(new Role(1L, RoleName.ROLE_ADMIN)));
        } else {
            worker.setRole(List.of(new Role(2L, RoleName.ROLE_USER)));
        }
        return mappingUtils.workerToDto(workerRepository.save(worker));
    }





}
