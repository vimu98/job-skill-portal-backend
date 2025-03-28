package com.jobskillportal.jobskillportalbackend.dto;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class UserUpdateDTO {
    private String name;
    private String email;
    private String profilePicture;


}