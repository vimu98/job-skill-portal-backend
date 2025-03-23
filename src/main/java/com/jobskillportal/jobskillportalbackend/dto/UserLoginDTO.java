package com.jobskillportal.jobskillportalbackend.dto;

import lombok.Data;

@Data
public class UserLoginDTO {
    private String email;
    private String password;
}
