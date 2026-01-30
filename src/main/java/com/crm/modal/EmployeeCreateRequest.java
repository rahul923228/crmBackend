package com.crm.modal;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeCreateRequest {
   
    private EmpBasicModal basic;
    private EmpFamilyModal family;
    private EmpWorkModal work;

}
