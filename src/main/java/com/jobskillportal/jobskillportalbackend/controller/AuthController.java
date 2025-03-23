package com.jobskillportal.jobskillportalbackend.controller;

import com.jobskillportal.jobskillportalbackend.dto.UserRegisterDTO;
import com.jobskillportal.jobskillportalbackend.dto.UserLoginDTO;
import com.jobskillportal.jobskillportalbackend.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody UserRegisterDTO userRegisterDTO) {
        return ResponseEntity.ok(userService.registerUser(userRegisterDTO));
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody UserLoginDTO userLoginDTO) {
        return ResponseEntity.ok(userService.loginUser(userLoginDTO));
    }
}
