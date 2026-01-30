package com.crm.repo;

import com.crm.entity.PasswordEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PasswordRepo  extends  JpaRepository<PasswordEntity, Long> {
    
}
