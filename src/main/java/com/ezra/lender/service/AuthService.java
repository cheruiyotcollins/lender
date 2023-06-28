package com.ezra.lender.service;


import com.ezra.lender.dto.AddRoleRequest;
import com.ezra.lender.dto.LoginDto;
import com.ezra.lender.dto.SignUpRequest;
import org.springframework.http.ResponseEntity;

public interface AuthService {
    String login(LoginDto loginDto);

    ResponseEntity<?> register(SignUpRequest signUpRequest);
    ResponseEntity<?> addRole(AddRoleRequest addRoleRequest);
}
