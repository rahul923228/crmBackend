package com.crm.repo;

import com.crm.entity.TaskAsignEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.crm.entity.EmpBasicEntity;
import com.crm.entity.TaskEntity;

import java.util.List;

@Repository
public interface TaskAsignRepo extends JpaRepository<TaskAsignEntity, Long>{

     boolean existsByTaskEntityAndBasicEntity(TaskEntity task, EmpBasicEntity employee);

    List<TaskAsignEntity> findByTaskEntity_Id(Long taskId);
    
}
