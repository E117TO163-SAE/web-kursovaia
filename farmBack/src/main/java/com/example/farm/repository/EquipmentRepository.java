package com.example.farm.repository;

import com.example.farm.entity.Equipment;
import com.example.farm.entity.EquipmentStatus;
import org.hibernate.sql.ast.tree.expression.JdbcParameter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EquipmentRepository extends JpaRepository<Equipment, Long> {


    List<Equipment> findByEquipmentStatus(EquipmentStatus equipmentStatus);


    List<Equipment> findByEquipmentTypeContainingIgnoreCase(String equipmentType);

    // Найти всё неисправное оборудование (BROKEN или MAINTENANCE)
    /*@Query("SELECT e FROM Equipment e WHERE e.equipmentStatus ='REPAIR'")
    List<Equipment> findBrokenOrMaintenance();

    // Найти всё рабочее оборудование
    @Query("SELECT e FROM Equipment e WHERE e.equipmentStatus = 'OPERATIONAL'")
    List<Equipment> findOperationalEquipment();*/

    // Подсчитать количество оборудования по статусу
    @Query("SELECT e.equipmentStatus, COUNT(e) FROM Equipment e GROUP BY e.equipmentStatus")
    List<Object[]> countByStatus();

    // Найти оборудование, которое используется в задачах
    @Query("SELECT DISTINCT e FROM Equipment e JOIN Task t ON t.equipment = e WHERE t.taskStatus != 'COMPLETED'")
    List<Equipment> findEquipmentInActiveTasks();
}