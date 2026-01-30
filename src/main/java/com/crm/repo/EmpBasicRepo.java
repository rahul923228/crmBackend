package com.crm.repo;

import java.util.Optional;

import com.crm.entity.EmpBasicEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmpBasicRepo extends  JpaRepository<EmpBasicEntity, Long>{

 Optional<EmpBasicEntity> findByUser_Id(Long id);
    
    
}
