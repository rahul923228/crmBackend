package com.crm.repo;

import com.crm.entity.EmpFamilyEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmpFamilyRepo extends  JpaRepository<EmpFamilyEntity, Long>{
    
    EmpFamilyEntity findByBasic_Id(Long id);
}
