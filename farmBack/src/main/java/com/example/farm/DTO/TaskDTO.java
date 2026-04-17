package com.example.farm.DTO;

import com.example.farm.entity.TaskStatus;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;


@Data
public class TaskDTO {
    private Long id;
    private String title;
    private LocalDateTime data;
    private LocalDateTime deadline;
    private TaskStatus taskStatus;
    private Long worker;
    private Long equipmentId;
    private List<Long> animalIds;

    public boolean isOverdue() {
        return deadline != null && deadline.isBefore(LocalDateTime.now())
                && !"CLOSED".equals(taskStatus);
    }

    public boolean isActive() {
        return "OPEN".equals(taskStatus) || "IN_PROGRESS".equals(taskStatus);
    }
}