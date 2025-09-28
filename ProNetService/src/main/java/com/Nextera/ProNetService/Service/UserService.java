package com.Nextera.ProNetService.Service;

import com.Nextera.ProNetService.Model.User;
import com.Nextera.ProNetService.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public Optional<User> getCurrentUserInfo(String email) {
        return userRepository.findByEmail(email);
    }

    // Upload profile picture
    public void uploadProfilePicture(Integer userId, MultipartFile file) throws IOException {
        Optional<User> userOpt = userRepository.findById(userId);
        if (userOpt.isPresent()) {
            User user = userOpt.get();
            user.setProfilePicture(file.getBytes());
            user.setProfilePictureContentType(file.getContentType());
            userRepository.save(user);
        } else {
            throw new RuntimeException("User not found with ID: " + userId);
        }
    }

    // Delete profile picture
    public void deleteProfilePicture(Integer userId) {
        Optional<User> userOpt = userRepository.findById(userId);
        if (userOpt.isPresent()) {
            User user = userOpt.get();
            user.setProfilePicture(null);
            user.setProfilePictureContentType(null);
            userRepository.save(user);
        } else {
            throw new RuntimeException("User not found with ID: " + userId);
        }
    }

    // Get profile picture
    // Get profile picture as byte[]
    public Optional<User> getProfilePicture(Integer userId) {
        return userRepository.findById(userId);
    }
}
