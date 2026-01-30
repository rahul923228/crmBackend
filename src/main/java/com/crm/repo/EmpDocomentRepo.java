package com.crm.repo;

import java.util.Optional;

import com.crm.entity.EmpDocomentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.crm.entity.EmpBasicEntity;

@Repository
public interface EmpDocomentRepo extends JpaRepository<EmpDocomentEntity, Long>{
    
    Optional<EmpDocomentEntity> findByBasic(EmpBasicEntity basicEntity);
   
}
