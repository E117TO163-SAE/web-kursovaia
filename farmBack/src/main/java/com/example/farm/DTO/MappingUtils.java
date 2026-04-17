package com.example.farm.DTO;

import com.example.farm.entity.*;
import com.example.farm.repository.AnimalRepository;
import com.example.farm.repository.EquipmentRepository;
import com.example.farm.repository.PlaceRepository;
import com.example.farm.repository.WorkerRepository;
import org.springframework.stereotype.Service;

import java.io.Console;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class MappingUtils {
    WorkerRepository workerRepository;
    EquipmentRepository equipmentRepository;
    AnimalRepository animalRepository;
    PlaceRepository placeRepository;
    public MappingUtils(WorkerRepository workerRepository, EquipmentRepository equipmentRepository,
                        AnimalRepository animalRepository, PlaceRepository placeRepository){
        this.workerRepository = workerRepository;
        this.equipmentRepository = equipmentRepository;
        this.animalRepository = animalRepository;
        this.placeRepository = placeRepository;
    }

    public AnimalDTO animalToDto(Animal animal) {
        AnimalDTO animalDto = new AnimalDTO();

        animalDto.setId(animal.getId());
        animalDto.setClassion(animal.getClassion());
        animalDto.setGender(animal.getGender());
        animalDto.setBirthday(animal.getBirthday());
        animalDto.setHealthStatus(animal.getHealthStatus());

        animalDto.setPlaceId(animal.getPlace().getPlaceId());
        animalDto.setPlaceName(animal.getPlace().getPlaceName());

        return animalDto;
    }

    public Animal animalToEntity(AnimalDTO animalDto) {
        Animal animalEntity = new Animal();
        Place place = placeRepository.findById(animalDto.getPlaceId()).orElseThrow();

        animalEntity.setId(animalDto.getId());
        animalEntity.setClassion(animalDto.getClassion());
        animalEntity.setGender(animalDto.getGender());
        animalEntity.setBirthday(animalDto.getBirthday());
        animalEntity.setHealthStatus(animalDto.getHealthStatus());

        animalEntity.setPlace(place);

        return animalEntity;
    }



    public PlaceDTO placeToDto(Place placeEntity) {
        PlaceDTO placeDto = new PlaceDTO();

        placeDto.setId(placeEntity.getPlaceId());
        placeDto.setPlaceName(placeEntity.getPlaceName());
        placeDto.setTemperature(placeEntity.getTemperature());
        placeDto.setHumidity(placeEntity.getHumidity());

        return placeDto;
    }

    public Place placeToEntity(PlaceDTO placeDto) {
        Place placeEntity = new Place();

        placeEntity.setPlaceId(placeDto.getId());
        placeEntity.setPlaceName(placeDto.getPlaceName());
        placeEntity.setTemperature(placeDto.getTemperature());
        placeEntity.setHumidity(placeDto.getHumidity());

        return placeEntity;
    }


    public EquipmentDTO equipmentToDto(Equipment equipmentEntity) {
        EquipmentDTO equipmentDto = new EquipmentDTO();

        equipmentDto.setId(equipmentEntity.getId());
        equipmentDto.setEquipmentType(equipmentEntity.getEquipmentType());
        equipmentDto.setEquipmentName(equipmentEntity.getEquipmentName());
        equipmentDto.setEquipmentStatus(equipmentEntity.getEquipmentStatus());

        return equipmentDto;
    }

    public Equipment equipmentToEntity(EquipmentDTO equipmentDto) {
        Equipment equipmentEntity = new Equipment();

        equipmentEntity.setId(equipmentDto.getId());
        equipmentEntity.setEquipmentType(equipmentDto.getEquipmentType());
        equipmentEntity.setEquipmentName(equipmentDto.getEquipmentName());
        equipmentEntity.setEquipmentStatus(equipmentDto.getEquipmentStatus());

        return equipmentEntity;
    }



    public TaskDTO taskToDto(Task taskEntity) {
        TaskDTO taskDto = new TaskDTO();

        taskDto.setId(taskEntity.getId());
        taskDto.setTitle(taskEntity.getTitle());
        taskDto.setData(taskEntity.getData());
        taskDto.setDeadline(taskEntity.getDeadline());
        taskDto.setTaskStatus(taskEntity.getTaskStatus());

        if(taskEntity.getWorker() != null){
            taskDto.setWorker(taskEntity.getWorker().getId());
        }
        if(taskEntity.getEquipment() != null){
            taskDto.setEquipmentId(taskEntity.getEquipment().getId());
        }
        if(taskEntity.getAnimals() != null){
            taskDto.setAnimalIds(taskEntity.getAnimals().stream()
                    .map(Animal::getId).collect(Collectors.toList()));
        }



        return taskDto;
    }

    public Task taskToEntity(TaskDTO taskDto) {
        Task taskEntity = new Task();

        Worker worker = workerRepository.findById(taskDto.getWorker())
                .orElseThrow(() -> new RuntimeException("Not found worker with this ID"));



        taskEntity.setId(taskDto.getId());
        taskEntity.setTitle(taskDto.getTitle());
        taskEntity.setDeadline(taskDto.getDeadline());
        taskEntity.setTaskStatus(taskDto.getTaskStatus());

        taskEntity.setWorker(worker);

        if (taskDto.getEquipmentId() != null) {
            Equipment equipment = equipmentRepository.findById(taskDto.getEquipmentId()).orElseThrow();
            taskEntity.setEquipment(equipment);
        }
        if(taskDto.getAnimalIds() != null ){
            List<Animal> animals = animalRepository.findAllById(taskDto.getAnimalIds());
            taskEntity.setAnimals(animals);
        }


        return taskEntity;
    }


    public WorkerDTO workerToDto(Worker workerEntity) {
        WorkerDTO workerDto = new WorkerDTO();

        workerDto.setId(workerEntity.getId());
        workerDto.setLogin(workerEntity.getLogin());
        workerDto.setSurname(workerEntity.getSurname());
        workerDto.setFirstname(workerEntity.getFirstname());
        workerDto.setPatronymic(workerEntity.getPatronymic());

        workerDto.setRoles(workerEntity.getRole());


        return workerDto;
    }

    public Worker workerToEntity(WorkerDTO workerDto) {
        Worker workerEntity = new Worker();

        workerEntity.setId(workerDto.getId());
        workerEntity.setLogin(workerDto.getLogin());
        workerEntity.setSurname(workerDto.getSurname());
        workerEntity.setFirstname(workerDto.getFirstname());
        workerEntity.setPatronymic(workerDto.getPatronymic());
        workerEntity.setPassword(workerDto.getPassword());
        workerEntity.setRole(workerDto.getRoles());

        return workerEntity;
    }
}
