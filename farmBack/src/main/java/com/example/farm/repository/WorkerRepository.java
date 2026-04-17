package com.example.farm.repository;

import com.example.farm.entity.RoleName;
import com.example.farm.entity.Worker;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface WorkerRepository extends JpaRepository<Worker, Long> {

    // Найти сотрудника по логину (для аутентификации)
    Optional<Worker> findByLogin(String login);


    // Найти сотрудников по фамилии (содержит)
    List<Worker> findBySurnameContainingIgnoreCase(String surname);

    // Найти сотрудников по имени
    List<Worker> findByFirstnameContainingIgnoreCase(String firstname);

    /*
    @Query("SELECT w FROM Worker w JOIN w.role r WHERE r.roleName = :roleName")
    List<Worker> findByRoleName(@Param("roleName") RoleName roleName);

    // Найти всех администраторов
    @Query("SELECT w FROM Worker w JOIN w.role r WHERE r.roleName = 'ADMIN'")
    List<Worker> findAllAdmins();

    // Найти всех обычных пользователей
    @Query("SELECT w FROM Worker w JOIN w.role r WHERE r.roleName = 'USER'")
    List<Worker> findAllUsers();*/

    // Найти сотрудников, у которых есть активные задачи
    @Query("SELECT DISTINCT w FROM Worker w JOIN w.tasks t WHERE t.taskStatus IN ('OPEN', 'IN_PROGRESS')")
    List<Worker> findWorkersWithActiveTasks();

    // Найти сотрудников без задач
    @Query("SELECT w FROM Worker w WHERE w.tasks IS EMPTY")
    List<Worker> findWorkersWithoutTasks();

    // Найти сотрудников, у которых нет активных задач (все задачи CLOSED или нет задач вообще)
    @Query("SELECT w FROM Worker w WHERE NOT EXISTS " +
            "(SELECT t FROM w.tasks t WHERE t.taskStatus IN ('OPEN', 'IN_PROGRESS'))")
    List<Worker> findWorkersWithNoActiveTasks();

}
