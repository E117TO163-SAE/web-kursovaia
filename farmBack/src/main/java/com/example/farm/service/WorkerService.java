package com.example.farm.service;

import com.example.farm.DTO.MappingUtils;
import com.example.farm.DTO.WorkerDTO;
import com.example.farm.entity.Role;
import com.example.farm.entity.RoleName;
import com.example.farm.entity.Worker;
import com.example.farm.repository.WorkerRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class WorkerService {

    private MappingUtils mappingUtils;
    private WorkerRepository workerRepository;
    private BCryptPasswordEncoder passwordEncoder;
    public WorkerService(MappingUtils mappingUtils, WorkerRepository workerRepository,
                         BCryptPasswordEncoder passwordEncoder){
        this.mappingUtils = mappingUtils;
        this.workerRepository = workerRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public List<WorkerDTO> findAll(){
        return workerRepository.findAll().stream().map(mappingUtils::workerToDto).toList();
    }

    public Optional<WorkerDTO> findById(Long id){
        return workerRepository.findById(id).map(mappingUtils::workerToDto);
    }

    public Optional<WorkerDTO> findByLogin(String login){
        return workerRepository.findByLogin(login).map(mappingUtils::workerToDto);
    }



    public void update(Long id, WorkerDTO newParam){
        WorkerDTO worker = findById(id).orElseThrow();

        worker.setSurname(newParam.getSurname());
        worker.setFirstname(newParam.getFirstname());
        worker.setPatronymic(newParam.getPatronymic());
        if(newParam.getPassword() != null || !newParam.getPassword().isEmpty()){

            worker.setPassword(passwordEncoder.encode(newParam.getPassword()) );
        }


        mappingUtils.workerToDto(workerRepository.save(mappingUtils.workerToEntity(worker)));
    }

    public void updatePassword(Long id, WorkerDTO newParam){
        WorkerDTO worker = findById(id).orElseThrow();
        worker.setPassword(newParam.getPassword());
        mappingUtils.workerToDto(workerRepository.save(mappingUtils.workerToEntity(worker)));
    }

    public void delete(Long id){
        workerRepository.deleteById(id);
    }
}
