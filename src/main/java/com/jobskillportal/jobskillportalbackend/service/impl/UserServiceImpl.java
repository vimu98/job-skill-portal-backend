package com.jobskillportal.jobskillportalbackend.service.impl;

import com.jobskillportal.jobskillportalbackend.dto.UserDTO;
import com.jobskillportal.jobskillportalbackend.dto.UserLoginDTO;
import com.jobskillportal.jobskillportalbackend.dto.UserRegisterDTO;
import com.jobskillportal.jobskillportalbackend.dto.UserUpdateDTO;
import com.jobskillportal.jobskillportalbackend.entity.User;
import com.jobskillportal.jobskillportalbackend.repo.UserRepository;
import com.jobskillportal.jobskillportalbackend.service.ImageUploadService;
import com.jobskillportal.jobskillportalbackend.service.UserService;
import com.jobskillportal.jobskillportalbackend.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder, JwtUtil jwtUtil) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
    }

    @Override
    public String registerUser(UserRegisterDTO userRegisterDTO) {
        // Check if user already exists
        if (userRepository.findByEmail(userRegisterDTO.getEmail()).isPresent()) {
            throw new RuntimeException("User already exists!");
        }

        // Create new User entity and populate data
        User user = new User();
        user.setName(userRegisterDTO.getName());
        user.setEmail(userRegisterDTO.getEmail());
        user.setPassword(passwordEncoder.encode(userRegisterDTO.getPassword()));
        user.setRole(userRegisterDTO.getRole());
        user.setProfilePicture(userRegisterDTO.getProfilePicture());

        userRepository.save(user);
        return "User registered successfully!";
    }

    @Override
    public String loginUser(UserLoginDTO userLoginDTO) {
        // Login logic (same as before)
        Optional<User> userOptional = userRepository.findByEmail(userLoginDTO.getEmail());
        if (userOptional.isEmpty() ||
                !passwordEncoder.matches(userLoginDTO.getPassword(), userOptional.get().getPassword())) {
            throw new RuntimeException("Invalid email or password");
        }
        return jwtUtil.generateToken(userLoginDTO.getEmail(), userOptional.get().getRole(), userOptional.get().getId());

    }

    @Override
    public UserDTO getUserById(Long userId) {
        Optional<User> userOptional = userRepository.findById(userId);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            return new UserDTO(user.getId(), user.getName(), user.getEmail(), user.getRole(), user.getProfilePicture());
        }
        throw new RuntimeException("User not found");
    }

    @Override
    public String updateUser(Long userId, UserUpdateDTO userUpdateDTO) {
        Optional<User> userOptional = userRepository.findById(userId);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            user.setName(userUpdateDTO.getName());
            user.setEmail(userUpdateDTO.getEmail());
            user.setProfilePicture(userUpdateDTO.getProfilePicture());

            userRepository.save(user);
            return "User updated successfully!";
        }
        return "User not found";
    }
}
