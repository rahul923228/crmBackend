package com.crm.repo;

import com.crm.entity.TaskAsignEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.crm.entity.EmpBasicEntity;
import com.crm.entity.TaskEntity;


import java.util.List;
import java.util.Optional;

@Repository
public interface TaskAsignRepo extends JpaRepository<TaskAsignEntity, Long>{

     boolean existsByTaskEntityAndBasicEntity(TaskEntity task, EmpBasicEntity employee);

    List<TaskAsignEntity> findByTaskEntity_Id(Long taskId);

    List<TaskAsignEntity> findByBasicEntity_Id(Long empId);

   Optional<TaskAsignEntity> findByBasicEntity_IdAndCustomerEntity_Id(Long empId, Long customerId);
}
