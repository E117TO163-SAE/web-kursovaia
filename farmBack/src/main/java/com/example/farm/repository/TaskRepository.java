package com.example.farm.repository;

import com.example.farm.entity.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {

    // Найти задачи по статусу
    //List<Task> findByTaskStatus(TaskStatus taskStatus);

    // Найти задачи с дедлайном до указанной даты
    List<Task> findByDeadlineBefore(LocalDateTime deadline);

    // Найти задачи с дедлайном после указанной даты
    List<Task> findByDeadlineAfter(LocalDateTime deadline);

    // Найти задачи, созданные в определённый период
    List<Task> findByDataBetween(LocalDateTime start, LocalDateTime end);

    // Найти просроченные задачи (дедлайн прошёл, а статус
    @Query("SELECT t FROM Task t WHERE t.deadline < :now AND t.taskStatus IN ('OPEN', 'IN_PROGRESS')")
    List<Task> findOverdueTasks(@Param("now") LocalDateTime now);

    /* Найти задачи, которые нужно выполнить сегодня (дедлайн сегодня)
    @Query("SELECT t FROM Task t WHERE DATE(t.deadline) = DATE(:date) AND t.taskStatus NOT IN ('COMPLETED', 'CANCELLED')")
    List<Task> findTasksForDate(@Param("date") LocalDateTime date);*/

    // Найти задачи сотрудника
    @Query("SELECT t FROM Task t JOIN t.worker w WHERE w.id = :workerId")
    List<Task> findAllByWorkerId(@Param("workerId") Long workerId);

    /* Найти задачи, связанные с животным
    @Query("SELECT t FROM Task t JOIN t.animals a WHERE a.id = :animalId")
    List<Task> findTasksByAnimalId(@Param("animalId") Long animalId);

    // Найти задачи, связанные с оборудованием
    List<Task> findByEquipmentId(Long equipmentId);*/

    // Подсчитать количество задач по статусу
    @Query("SELECT t.taskStatus, COUNT(t) FROM Task t GROUP BY t.taskStatus")
    List<Object[]> countByStatus();

    // Найти активные задачи
    @Query("SELECT t FROM Task t WHERE t.taskStatus IN ('OPEN', 'IN_PROGRESS')")
    List<Task> findActiveTasks();

    @Query("SELECT COUNT(t) FROM Task t WHERE t.taskStatus IN ('OPEN', 'IN_PROGRESS')")
    long countActiveTasks();

    /* Найти задачи с ближайшим дедлайном (первые 5)
    @Query("SELECT t FROM Task t WHERE t.taskStatus NOT IN ('COMPLETED', 'CANCELLED') ORDER BY t.deadline ASC LIMIT 5")
    List<Task> findTop5UpcomingTasks();*/
}