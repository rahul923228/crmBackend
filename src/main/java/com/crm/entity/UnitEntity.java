package com.crm.entity;

import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="unit")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UnitEntity {
    
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    Long id;

   private String companyName;

    private String companyCode;          // Optional (CRM001, COMP01)

    private String email;
    private String phone;

    private String address;

    private String website;

    private String industryType;          // IT, Finance, Healthcare

    private String status;                // ACTIVE / INACTIVE

    private LocalDate createdDate;


   @ManyToOne
   @JoinColumn(name="user_id")
   private UserEntity userEntity;
}
