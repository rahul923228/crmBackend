package com.crm.service;

import java.util.ArrayList;
import java.util.List;

import com.crm.entity.*;
import com.crm.modal.SupportAsignModal;
import com.crm.repo.*;
import org.springframework.beans.BeanUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class SupportAsignService {
    

    SupportAsignRepo repo;
    TaskAsignRepo taskAsignRepo;
    EmpBasicRepo basicRepo;
    CustomerRepo customerRepo;
    TicketRepo ticketRepo;

    public SupportAsignService(EmpBasicRepo basicRepo,TicketRepo ticketRepo, SupportAsignRepo repo, TaskAsignRepo taskAsignRepo,CustomerRepo customerRepo) {
        this.basicRepo = basicRepo;
        this.repo = repo;
        this.taskAsignRepo=taskAsignRepo;
        this.customerRepo=customerRepo;
        this.ticketRepo=ticketRepo;
    }

   


    public ResponseEntity<?> asignTicket(
        Long customerId,
        Long ticketId,
        List<Long> empIds
) {

    CustomerEntity customer =
        customerRepo.findById(customerId)
        .orElseThrow(() -> new RuntimeException("Customer not found"));

    TicketEntity ticket =
        ticketRepo.findById(ticketId)
        .orElseThrow(() -> new RuntimeException("Ticket not found"));

    // 🔒 Security check
    if (!ticket.getCustomer().getId().equals(customerId)) {
        throw new RuntimeException("Ticket does not belong to this customer");
    }

    for (Long empId : empIds) {

        EmpBasicEntity emp =
            basicRepo.findById(empId)
            .orElseThrow(() -> new RuntimeException("Employee not found"));

        boolean exists =
            repo.existsByTicketEntityAndBasicEntity(ticket, emp);

        if (exists) continue;

        SupportAsingEntity entity = new SupportAsingEntity();
        entity.setTicketEntity(ticket);
        entity.setBasicEntity(emp);
        entity.setCustomerEntity(customer);
        entity.setStatus("OPEN");

        repo.save(entity);
    }

    return ResponseEntity.ok("Support assigned successfully");
}


    public List<Long> getProjectEmployees(Long projectId) {

        List<TaskAsignEntity> assignments = taskAsignRepo.findByTaskEntity_Id(projectId);

        List<Long> empIds = new ArrayList<>();


        for (TaskAsignEntity asgn : assignments) {
            if (asgn.getBasicEntity() != null) {
                empIds.add(asgn.getBasicEntity().getId());
            }
        }
        return empIds;
    }


 public List<SupportAsignModal> getSupportA(
        Long ticketId,
        Long customerId
) {

    TicketEntity ticket =
        ticketRepo.findById(ticketId)
        .orElseThrow(() -> new RuntimeException("Ticket not found"));

    // 🔒 Ownership check
    if (!ticket.getCustomer().getId().equals(customerId)) {
        throw new RuntimeException("Ticket does not belong to this customer");
    }

    List<SupportAsignModal> listModals = new ArrayList<>();

    for (SupportAsingEntity entity : ticket.getAsignTicketList()) {

        SupportAsignModal modal=new SupportAsignModal();
       BeanUtils.copyProperties(entity, modal);

       modal.setCustomer_id(customerId);
       modal.setEmp_id(entity.getBasicEntity().getId());
       modal.setId(entity.getId());
       modal.setStatus(entity.getStatus());
       modal.setTicket_id(ticketId);
       
       listModals.add(modal);
       
       
    }

    return listModals;
}


   public List<SupportAsignModal> getSupportByEmp(Long empId){

        List<SupportAsingEntity> list=repo.findByBasicEntity_Id(empId);

        List<SupportAsignModal> modals=new ArrayList<>();

        if (list.isEmpty()){
            return new ArrayList<>();
        }

        list.forEach(entity->{

            SupportAsignModal modal=new SupportAsignModal();

            modal.setCustomer_id(entity.getCustomerEntity().getId());
            modal.setEmp_id(entity.getBasicEntity().getId());
            modal.setStatus(entity.getStatus());
            modal.setTicket_id(entity.getTicketEntity().getId());
            modal.setId(entity.getId());

            modals.add(modal);

        });

        return modals;
   }
}
