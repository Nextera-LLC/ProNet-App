package com.Nextera.ProNetService.Service;

import com.Nextera.ProNetService.Model.User;
import com.Nextera.ProNetService.Repository.UserRepository;
import com.Nextera.ProNetService.dto.JwtDto;
import com.Nextera.ProNetService.dto.LoginRequest;
import com.Nextera.ProNetService.dto.RegisterRequest;
import com.Nextera.ProNetService.util.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthService {
    @Autowired private UserRepository userRepo;
//    @Autowired private Jwt jwtUtil;
    @Autowired private JwtService jwtService;
    @Autowired private PasswordEncoder passwordEncoder;

    public User register(RegisterRequest request) {
        if (!userRepo.findByEmail(request.getEmail()).isEmpty()) {
            throw new RuntimeException("User already exists with this email, Please try with new Email");

        } else {
            User user = new User();
            user.setEmail(request.getEmail());
            user.setPassword(passwordEncoder.encode(request.getPassword()));
            user.setRole("USER");
            user.setFirstName(request.getFirstName());
            user.setLastName(request.getLastName());

            return userRepo.save(user);
        }
    }
    public JwtDto login(LoginRequest loginRequest) {
        // Try to find the user by email
        Optional<User> userOptional = userRepo.findByEmail(loginRequest.getEmail());

        // If user not found or password doesn't match, throw error
        if (userOptional.isEmpty() ||
                !passwordEncoder.matches(loginRequest.getPassword(), userOptional.get().getPassword())) {
            throw new RuntimeException("Invalid credentials");
        }

        JwtDto jwt = new JwtDto();
            jwt.setToken(jwtService.generateToken(loginRequest.getEmail()));
            return jwt;
        }
    }