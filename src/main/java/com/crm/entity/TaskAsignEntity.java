package com.crm.entity;

import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name="taskAsign")
@Data
public class TaskAsignEntity {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    Long id;

    @ManyToOne
    @JoinColumn(name="task_id")
    TaskEntity  taskEntity;

    @ManyToOne
    @JoinColumn(name="emp_id")
    EmpBasicEntity basicEntity;

    @ManyToOne
    @JoinColumn(name="customer_id")
    CustomerEntity customerEntity;

    private String status = "TODO";
    private LocalDate assignedDate = LocalDate.now();


    @PrePersist
    public void onCreate(){
        assignedDate=LocalDate.now();
    }
    
}
