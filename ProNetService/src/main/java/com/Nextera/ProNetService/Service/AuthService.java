package com.Nextera.ProNetService.Service;

import com.Nextera.ProNetService.Model.User;
import com.Nextera.ProNetService.Repository.UserRepository;
import com.Nextera.ProNetService.dto.JwtDto;
import com.Nextera.ProNetService.dto.LoginRequest;
import com.Nextera.ProNetService.dto.RegisterRequest;
import com.Nextera.ProNetService.util.Jwt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    @Autowired private UserRepository userRepo;
    @Autowired private Jwt jwtUtil;
    @Autowired private PasswordEncoder passwordEncoder;

    public User register(RegisterRequest request){
        User user = new User();
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRole("USER");
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
//        user.setConfirmedPassword(request.getConfirmPassword());

        return userRepo.save(user);
    }
    public JwtDto login(LoginRequest loginRequest) {
        // AuthService.java
        User user = userRepo.findByEmail(loginRequest.getEmail())
                    .orElseThrow(() -> new RuntimeException("User not found"));

            if (!passwordEncoder.matches(loginRequest.getPassword(), user.getPassword())) {
                throw new RuntimeException("Invalid credentials");
            }

        JwtDto jwt = new JwtDto();
            jwt.setToken(jwtUtil.generateToken(loginRequest.getEmail()));
            return jwt;
        }
    }