package com.crm.repo;

import com.crm.entity.EmpWorkEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface  EmpWorkRepo extends JpaRepository<EmpWorkEntity,Long>{
    
    EmpWorkEntity findByBasic_Id(Long empID);
}
