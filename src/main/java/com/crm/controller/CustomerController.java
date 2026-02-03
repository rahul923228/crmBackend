package com.crm.controller;

import java.util.List;

import com.crm.modal.CustomerModal;
import com.crm.service.CustomerService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public  class CustomerController{


    CustomerService service;

    public CustomerController(CustomerService service) {
        this.service = service;
    }


    @PostMapping("/addCustomer/{id}")
    public ResponseEntity<?> addCustomer(@RequestBody CustomerModal modal, @PathVariable Long id){

        return  service.addCustomer(modal,id);
    }


    @GetMapping("/getCustomer/{id}")
    public List<CustomerModal> getCustomerById(@PathVariable Long id){
        
        return service.getCustomerById(id);
    }

    @GetMapping("/allCustomer")
    public List<CustomerModal> getAllCustomer(){

        return service.getAllCustomer();
    }

    @PutMapping("customer/{customerId}")
    public ResponseEntity<?> changeStatus(@PathVariable  Long customerId,@RequestParam String status){

        return service.changeStatus(customerId,status);
    }

}