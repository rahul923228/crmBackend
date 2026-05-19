package com.crm.service;

import com.crm.User.*;
import com.crm.entity.CustomerEntity;
import com.crm.entity.EmpBasicEntity;
import com.crm.entity.PasswordEntity;
import com.crm.entity.UserEntity;
import com.crm.modal.UserRequest;
import com.crm.modal.UserResponce;
import com.crm.repo.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class UserService {

    UserRepo userRepo;
    EmpBasicRepo basicRepo;
    JwtiUtil jwtiUtil;
    PasswordEncoder encoder;
    UnitRepo unitRepo;
    PasswordRepo passwordRepo;
    CustomerRepo customerRepo;

    public UserService(PasswordEncoder encoder, JwtiUtil jwtiUtil, UserRepo userRepo, EmpBasicRepo basicRepo,
            UnitRepo unitRepo, PasswordRepo passwordRepo, CustomerRepo customerRepo) {
        this.encoder = encoder;
        this.jwtiUtil = jwtiUtil;
        this.userRepo = userRepo;
        this.basicRepo = basicRepo;
        this.unitRepo = unitRepo;
        this.passwordRepo = passwordRepo;
        this.customerRepo = customerRepo;
    }

    public ResponseEntity<?> login(UserRequest userRequest) {

        UserEntity user = userRepo
                .findByUserName(userRequest.getUserName())
                .orElse(null);

        // ❌ WRONG → 400
        // ✅ FIX → 401
        if (user == null) {
            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .body("Invalid username or password");
        }

        if (!encoder.matches(userRequest.getPassword(), user.getPassword())) {
            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .body("Invalid username or password");
        }

        String token = jwtiUtil.genrateToken(user.getUserName());

        System.out.println("token???"+token);

        UserResponce response = new UserResponce();
        response.setToken(token);
        response.setRole(user.getRole());
        response.setUserName(user.getUserName());
        response.setUserId(user.getId());

        // CUSTOMER
        if ("CUSTOMER".equals(user.getRole())) {
            CustomerEntity customerEntity = customerRepo
                    .findByUserEntity_Id(user.getId())
                    .orElseThrow(() -> new RuntimeException("Customer not found"));

            response.setCustomerId(customerEntity.getId());
        }

        // EMPLOYEE
        if ("EMPLOYEE".equals(user.getRole())) {
            EmpBasicEntity empBasicEntity = basicRepo.findByUser_Id(user.getId())
                    .orElseThrow(() -> new RuntimeException("Employee not found"));

            response.setEmpId(empBasicEntity.getId());
        }

        return ResponseEntity.ok(response);
    }

    public UserResponce register(UserRequest request) {

        if (userRepo.existsByUserName(request.getUserName().trim())) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "Username already exists");
        }

        UserEntity user = new UserEntity();
        PasswordEntity password = new PasswordEntity();

        String username = request.getUserName().trim().toLowerCase();
        user.setUserName(username);
        user.setPassword(encoder.encode(request.getPassword()));

        password.setUserName(username);
        password.setPassword(request.getPassword());

        passwordRepo.save(password);

        user.setRole(request.getRole()); // e.g. ADMIN / USER

        userRepo.save(user);
        String token = jwtiUtil.genrateToken(user.getUserName());
         UserResponce response = new UserResponce();
    response.setToken(token);
    response.setRole(user.getRole());
    response.setUserName(user.getUserName());
    response.setUserId(user.getId()); 

        return  response;

    }

    // String token=jwtiUtil.genrateToken(user.getUserName());
    // return new UserResponce(token, user.getRole(),
    // user.getUserName(),user.getId(),null);
}
