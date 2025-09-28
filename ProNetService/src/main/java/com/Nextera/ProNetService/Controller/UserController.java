package com.Nextera.ProNetService.Controller;

import com.Nextera.ProNetService.Model.User;
import com.Nextera.ProNetService.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/all")
    public ResponseEntity<List<User>> getAllUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
    }

    @GetMapping("/current")
    public ResponseEntity<Optional<User>> getCurrentUserInfo(Principal principal) {
        Optional<User> user = userService.getCurrentUserInfo(principal.getName());
        return ResponseEntity.ok(user);
    }

    @PostMapping("/{id}/profile-picture")
    public ResponseEntity<String> uploadProfilePicture(@PathVariable Integer id,
                                                       @RequestParam("file") MultipartFile file) {
        try {
            userService.uploadProfilePicture(id, file);
            return ResponseEntity.ok("Profile picture uploaded successfully.");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        }
    }

    @DeleteMapping("/{id}/profile-picture")
    public ResponseEntity<String> deleteProfilePicture(@PathVariable Integer id) {
        try {
            userService.deleteProfilePicture(id);
            return ResponseEntity.ok("Profile picture deleted successfully.");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        }
    }

    @GetMapping("/{id}/profile-picture")
    public ResponseEntity<byte[]> getProfilePicture(@PathVariable Integer id) {
        Optional<User> userOpt = userService.getProfilePicture(id);

        if (userOpt.isPresent()) {
            User user = userOpt.get();
            if (user.getProfilePicture() != null) {
                System.out.println("Returning profile picture for user: " + id);
                return ResponseEntity.ok()
                        .header("Content-Type", user.getProfilePictureContentType())
                        .body(user.getProfilePicture());
            } else {
                System.out.println("User has no profile picture: " + id);
                return ResponseEntity.notFound().build();
            }
        } else {
            System.out.println("User not found: " + id);
            return ResponseEntity.notFound().build();
        }
    }
}
