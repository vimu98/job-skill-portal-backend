package com.jobskillportal.jobskillportalbackend.dto;

import lombok.Data;

@Data
public class UserRegisterDTO {
    private String name;
    private String email;
    private String password;
    private String role;
    private String profilePicture;
}