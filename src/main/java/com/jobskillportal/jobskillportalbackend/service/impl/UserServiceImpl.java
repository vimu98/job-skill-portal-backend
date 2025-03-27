package com.jobskillportal.jobskillportalbackend.service.impl;

import com.jobskillportal.jobskillportalbackend.dto.UserDTO;
import com.jobskillportal.jobskillportalbackend.dto.UserRegisterDTO;
import com.jobskillportal.jobskillportalbackend.dto.UserLoginDTO;
import com.jobskillportal.jobskillportalbackend.dto.UserUpdateDTO;
import com.jobskillportal.jobskillportalbackend.entity.User;
import com.jobskillportal.jobskillportalbackend.repo.UserRepository;
import com.jobskillportal.jobskillportalbackend.util.JwtUtil;
import com.jobskillportal.jobskillportalbackend.service.UserService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder, JwtUtil jwtUtil) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
    }

    public String registerUser(UserRegisterDTO userRegisterDTO) {
        if (userRepository.findByEmail(userRegisterDTO.getEmail()).isPresent()) {
            throw new RuntimeException("User already exists!");
        }

        User user = new User();
        user.setName(userRegisterDTO.getName());
        user.setEmail(userRegisterDTO.getEmail());
        user.setPassword(passwordEncoder.encode(userRegisterDTO.getPassword()));
        user.setRole(userRegisterDTO.getRole());


        userRepository.save(user);
        return "User registered successfully!";
    }

    public String loginUser(UserLoginDTO userLoginDTO) {
        Optional<User> userOptional = userRepository.findByEmail(userLoginDTO.getEmail());
        if (userOptional.isEmpty() ||
                !passwordEncoder.matches(userLoginDTO.getPassword(), userOptional.get().getPassword())) {
            throw new RuntimeException("Invalid email or password");
        }

        return jwtUtil.generateToken(userLoginDTO.getEmail(), userOptional.get().getRole(), userOptional.get().getId());
    }



    public UserDTO getUserById(Long userId) {
        Optional<User> userOptional = userRepository.findById(userId);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            return new UserDTO(user.getId(), user.getName(), user.getEmail(), user.getRole());
        }
        throw new RuntimeException("User not found");
    }


    public String updateUser(Long userId, UserUpdateDTO userUpdateDTO) {
        Optional<User> userOptional = userRepository.findById(userId);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            user.setName(userUpdateDTO.getName());
            user.setEmail(userUpdateDTO.getEmail());
            userRepository.save(user);
            return "User updated successfully!";
        }
        return "User not found";
    }

}
