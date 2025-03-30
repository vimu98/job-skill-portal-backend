package com.jobskillportal.jobskillportalbackend.controller;

import com.jobskillportal.jobskillportalbackend.dto.UserDTO;
import com.jobskillportal.jobskillportalbackend.dto.UserLoginDTO;
import com.jobskillportal.jobskillportalbackend.dto.UserRegisterDTO;
import com.jobskillportal.jobskillportalbackend.dto.UserUpdateDTO;
import com.jobskillportal.jobskillportalbackend.service.ImageUploadService;
import com.jobskillportal.jobskillportalbackend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final UserService userService;

    @Autowired
    private ImageUploadService imageUploadService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestPart("name") String name,
                                           @RequestPart("email") String email,
                                           @RequestPart("password") String password,
                                           @RequestPart("role") String role,
                                           @RequestPart(value = "profilePicture", required = false) MultipartFile profilePicture) {

        UserRegisterDTO userRegisterDTO = new UserRegisterDTO();
        userRegisterDTO.setName(name);
        userRegisterDTO.setEmail(email);
        userRegisterDTO.setPassword(password);
        userRegisterDTO.setRole(role);


        if (profilePicture != null && !profilePicture.isEmpty()) {
            String profilePictureUrl = imageUploadService.uploadFile(profilePicture); // Upload image
            userRegisterDTO.setProfilePicture(profilePictureUrl);
        }


        return ResponseEntity.ok(userService.registerUser(userRegisterDTO));
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody UserLoginDTO userLoginDTO) {
        return ResponseEntity.ok(userService.loginUser(userLoginDTO));
    }

    @GetMapping("/user/{id}")
    public ResponseEntity<UserDTO> getUser(@PathVariable Long id) {
        return ResponseEntity.ok(userService.getUserById(id));
    }

    @PutMapping("/user/{id}")
    public ResponseEntity<String> updateUser(@PathVariable Long id,
                                             @RequestPart("name") String name,
                                             @RequestPart("email") String email,
                                             @RequestPart("password") String password,
                                             @RequestPart(value = "profilePicture", required = false) MultipartFile profilePicture) {

        UserUpdateDTO userUpdateDTO = new UserUpdateDTO();
        userUpdateDTO.setName(name);
        userUpdateDTO.setEmail(email);
        userUpdateDTO.setPassword(password);

        if (profilePicture != null && !profilePicture.isEmpty()) {
            String profilePictureUrl = imageUploadService.uploadFile(profilePicture); // Upload image
            userUpdateDTO.setProfilePicture(profilePictureUrl);
        }

        return ResponseEntity.ok(userService.updateUser(id, userUpdateDTO));
    }
}
