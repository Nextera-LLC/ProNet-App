package com.Nextera.ProNetService.Service;

import com.Nextera.ProNetService.Model.User;
import com.Nextera.ProNetService.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public User getCurrentUserInfo(String email){
        userRepository.findByEmail(email);
        return userRepository.findByEmail(email).get();
    }

    public List<User> getAllUsers() {

        return userRepository.findAll();
    }
}
