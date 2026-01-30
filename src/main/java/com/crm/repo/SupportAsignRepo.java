package com.crm.repo;

import com.crm.entity.SupportAsingEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.crm.entity.EmpBasicEntity;
import com.crm.entity.TicketEntity;

@Repository
public interface  SupportAsignRepo extends  JpaRepository<SupportAsingEntity, Long> {
    
    boolean existsByTicketEntityAndBasicEntity(TicketEntity ticketEntity,EmpBasicEntity basicEntity);
}
