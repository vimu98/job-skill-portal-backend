package com.jobskillportal.jobskillportalbackend.service;

import com.jobskillportal.jobskillportalbackend.dto.UserDTO;
import com.jobskillportal.jobskillportalbackend.dto.UserLoginDTO;
import com.jobskillportal.jobskillportalbackend.dto.UserRegisterDTO;
import com.jobskillportal.jobskillportalbackend.dto.UserUpdateDTO;

public interface UserService {
    String registerUser(UserRegisterDTO userRegisterDTO);

    String loginUser(UserLoginDTO userLoginDTO);

    UserDTO getUserById(Long userId);

    String updateUser(Long userId, UserUpdateDTO userUpdateDTO);
}
