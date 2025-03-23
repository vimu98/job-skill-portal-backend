package com.jobskillportal.jobskillportalbackend.service;

import com.jobskillportal.jobskillportalbackend.dto.UserLoginDTO;
import com.jobskillportal.jobskillportalbackend.dto.UserRegisterDTO;

public interface UserService {
    String registerUser(UserRegisterDTO userRegisterDTO);

    String loginUser(UserLoginDTO userLoginDTO);
}
