package com.crm.repo;

import java.util.List;

import com.crm.entity.TicketEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface TicketRepo extends JpaRepository<TicketEntity, Long>{

    List<TicketEntity> findByCustomer_Id(Long customerId);


    Optional<TicketEntity> findByProject_id(Long id);
    
}
