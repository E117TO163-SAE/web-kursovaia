package com.example.farm.controller;

import com.example.farm.DTO.WorkerDTO;
import com.example.farm.entity.Role;
import com.example.farm.entity.RoleName;
import com.example.farm.service.CustomUserDetailsService;
import com.example.farm.service.WorkerService;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/workers")
public class WorkerController {
    private CustomUserDetailsService customUserDetailsService;
    private WorkerService workerService;
    public WorkerController(WorkerService workerService, CustomUserDetailsService customUserDetailsService){
        this.workerService = workerService;
        this.customUserDetailsService = customUserDetailsService;
    }

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public WorkerDTO register(@RequestBody WorkerDTO worker){
        return customUserDetailsService.register(worker);

    }

    @GetMapping
    public List<WorkerDTO> findAll(){
        return workerService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<WorkerDTO> findById(@PathVariable Long id){
        return workerService.findById(id).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/login/{login}")
    public ResponseEntity<WorkerDTO> findByLogin(@PathVariable String login){
        return workerService.findByLogin(login).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updateWorker(@PathVariable Long id ,@RequestBody WorkerDTO newParam){
        if(newParam == null){
            return ResponseEntity.notFound().build();
        }
        workerService.update(id, newParam);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/uppass/{id}")
    public ResponseEntity<Void> updatePassword(@PathVariable Long id ,@RequestBody WorkerDTO newParam){
        if(newParam == null){
            return ResponseEntity.notFound().build();
        }
        workerService.update(id, newParam);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id){

        if (workerService.findById(id).isEmpty()) {
            return ResponseEntity.badRequest().build();
        }

        workerService.delete(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/me")
    public ResponseEntity<UserDetails> getLoginAndPass(@RequestBody String str) {
        UserDetails user = customUserDetailsService.loadUserByUsername(str);
        return ResponseEntity.ok(user);

    }

}
