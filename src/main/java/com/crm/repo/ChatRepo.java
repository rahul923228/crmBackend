package com.crm.repo;

import java.util.List;

import com.crm.entity.ChatEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChatRepo  extends  JpaRepository<ChatEntity, Long>{
     List<ChatEntity> findByTicket_IdOrderByCreatedAtAsc(Long ticketId);

}
