package com.crm.repo;

import java.util.Optional;

import com.crm.entity.DepartMentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DepartmentRepo extends JpaRepository<DepartMentEntity, Long>{

  Optional<DepartMentEntity> findByDepartmentName(String department);
    
}
