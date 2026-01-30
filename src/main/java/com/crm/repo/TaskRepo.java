package com.crm.repo;

import com.crm.entity.TaskEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface  TaskRepo extends JpaRepository<TaskEntity, Long>{
    
}
