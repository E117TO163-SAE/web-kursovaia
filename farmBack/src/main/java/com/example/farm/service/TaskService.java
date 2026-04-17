package com.example.farm.service;

import com.example.farm.DTO.MappingUtils;
import com.example.farm.DTO.TaskDTO;
import com.example.farm.DTO.WorkerDTO;
import com.example.farm.entity.RoleName;
import com.example.farm.entity.Worker;
import com.example.farm.repository.TaskRepository;
import com.example.farm.repository.WorkerRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TaskService {

    private MappingUtils mappingUtils;
    private TaskRepository taskRepository;
    private WorkerRepository workerRepository;
    public TaskService(MappingUtils mappingUtils, TaskRepository taskRepository, WorkerRepository workerRepository){
        this.mappingUtils = mappingUtils;
        this.taskRepository = taskRepository;
        this.workerRepository = workerRepository;
    }

    public List<TaskDTO> findAll(){
        return taskRepository.findAll().stream().map(mappingUtils::taskToDto).toList();
    }

    public Optional<TaskDTO> findById(Long id){
        return taskRepository.findById(id).map(mappingUtils::taskToDto);
    }

    public TaskDTO create(TaskDTO taskDTO){
        taskDTO.setId(null);
        //Optional<Worker> worker = workerRepository.findByLogin(login);
        //taskDTO.setWorker(worker.get().getId());
        return mappingUtils.taskToDto(taskRepository.save(mappingUtils.taskToEntity(taskDTO)));
    }

    public void update(Long id, TaskDTO newParam){
        TaskDTO task = findById(id).orElseThrow();


        task.setTitle(newParam.getTitle());
        task.setDeadline(newParam.getDeadline());
        task.setWorker(newParam.getWorker());
        task.setEquipmentId(newParam.getEquipmentId());
        task.setAnimalIds(newParam.getAnimalIds());
        //когда будет авторизация -- добавить разделение,
        // что пользователь может менять только статус
        task.setTaskStatus(newParam.getTaskStatus());

        mappingUtils.taskToDto(taskRepository.save(mappingUtils.taskToEntity(task)));
    }

    public void delete(Long id){
        taskRepository.deleteById(id);
    }

    public List<TaskDTO> findAllByWorkerId(Long id){
        return taskRepository.findAllByWorkerId(id).stream().map(mappingUtils::taskToDto).toList();
    }
    
}
