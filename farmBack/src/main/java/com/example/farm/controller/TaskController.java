package com.example.farm.controller;

import com.example.farm.DTO.TaskDTO;
import com.example.farm.entity.Task;
import com.example.farm.service.TaskService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/tasks")
public class TaskController {

    private TaskService taskServise;

    public TaskController(TaskService taskServise){
        this.taskServise = taskServise;
    }

    @GetMapping
    public List<TaskDTO> findAll(){
        return taskServise.findAll();
    }
    @GetMapping("/{id}")
    public ResponseEntity<TaskDTO> findById(@PathVariable Long id){
        return taskServise.findById(id).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }


    @GetMapping("/worker/{id}")
    public List<TaskDTO> findAllByWorkerId(@PathVariable Long id){
        return taskServise.findAllByWorkerId(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<TaskDTO> create(@RequestBody TaskDTO task){
        task = taskServise.create(task);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(task.getId())
                .toUri();
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(location);
        return new ResponseEntity<>(task, headers, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> update(@PathVariable Long id, @RequestBody TaskDTO newParam){
        if(newParam == null){
            return ResponseEntity.notFound().build();
        }

        taskServise.update(id, newParam);

        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id){
        Optional<TaskDTO> taskOptional = taskServise.findById(id);

        if (taskOptional.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }

        taskServise.delete(id);
        return ResponseEntity.noContent().build();
    }

}
