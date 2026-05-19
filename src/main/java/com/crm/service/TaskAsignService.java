package com.crm.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.crm.entity.*;
import com.crm.modal.TaskAsignModal;
import com.crm.repo.SupportAsignRepo;
import com.crm.repo.TaskAsignRepo;
import org.springframework.stereotype.Service;

import com.crm.repo.EmpBasicRepo;
import com.crm.repo.TaskRepo;

import jakarta.transaction.Transactional;

@Service
public class TaskAsignService {

    private final TaskAsignRepo asignRepo;
    private final EmpBasicRepo basicRepo;
    private final TaskRepo taskRepo;
    private final SupportAsignRepo supportAsignRepo;

    public TaskAsignService(TaskAsignRepo asignRepo,
                            EmpBasicRepo basicRepo,
                            TaskRepo taskRepo, SupportAsignRepo supportAsignRepo) {
        this.asignRepo = asignRepo;
        this.basicRepo = basicRepo;
        this.taskRepo = taskRepo;
        this.supportAsignRepo = supportAsignRepo;
    }

    @Transactional
    public void assignTaskToEmployees(Long taskId, List<Long> empIds) {

        TaskEntity task = taskRepo.findById(taskId)
                .orElseThrow(() -> new RuntimeException("Task not found"));




        for (Long empId : empIds) {

            EmpBasicEntity employee = basicRepo.findById(empId)
                    .orElseThrow(() -> new RuntimeException("Employee not found"));

            boolean exists =
                    asignRepo.existsByTaskEntityAndBasicEntity(task, employee);

            if (exists) {
                continue;
            }

                TaskAsignEntity asign = new TaskAsignEntity();
                asign.setTaskEntity(task);
                asign.setBasicEntity(employee);
                asign.setCustomerEntity(task.getCustomerEntity());
                asign.setStatus("Open");


            SupportAsingEntity asingEntity=new SupportAsingEntity();

            asingEntity.setAssignedDate(LocalDate.now());
            asingEntity.setBasicEntity(employee);
            asingEntity.setStatus("OPEN");
            asingEntity.setCustomerEntity(task.getCustomerEntity());
            asignRepo.save(asign);
            supportAsignRepo.save(asingEntity);


        }
    }


    public List<TaskAsignModal> getAsignTask(Long taskId){

              TaskEntity taskEntity=taskRepo.findById(taskId).orElseThrow(()-> new RuntimeException("task not found"));

              List<TaskAsignEntity> list=taskEntity.getTaskAsignList();
              List<TaskAsignModal> modals=new ArrayList<>();

              list.stream().forEach(entity->{

                TaskAsignModal  modal =new TaskAsignModal();

                modal.setId(entity.getId());
                modal.setEmp_id(entity.getBasicEntity().getId());
                modal.setTask_id(entity.getTaskEntity().getId());
                modal.setStatus(entity.getStatus());
                modal.setAssignedDate(entity.getAssignedDate());
                modal.setCustomer_id(taskEntity.getCustomerEntity().getId());

                modals.add(modal);

              });

              return modals;



    }


    public void updateStatus(Long empId,Long customerId,String status){

   TaskAsignEntity asignEntity= asignRepo.findByBasicEntity_IdAndCustomerEntity_Id(empId,customerId).orElseThrow(()-> new RuntimeException("not founde"));

     asignEntity.setStatus(status);

     asignRepo.save(asignEntity);
    }


    public List<TaskAsignModal> getAssingTaskByEmpId(Long empId){

      List<TaskAsignEntity>list= asignRepo.findByBasicEntity_Id(empId);

      List<TaskAsignModal> modals=new ArrayList<>();

      if (list.isEmpty()){
          return new ArrayList<>();
      }

      list.forEach(entity->{
          TaskAsignModal modal=new TaskAsignModal();

          modal.setTask_id(entity.getTaskEntity().getId());
          modal.setStatus(entity.getTaskEntity().getStatus());
          modal.setEmp_id(entity.getBasicEntity().getId());
          modal.setCustomer_id(entity.getCustomerEntity().getId());
          modal.setAssignedDate(entity.getAssignedDate());
          modal.setId(entity.getId());
          modal.setName(entity.getTaskEntity().getName());
          modal.setDescription(entity.getTaskEntity().getDescription());

          modals.add(modal);
      });

      return modals;
    }


}
