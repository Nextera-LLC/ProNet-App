package com.Nextera.ProNetService.Controller.Profile;

import com.Nextera.ProNetService.Model.User;
import com.Nextera.ProNetService.Service.Profile.UserProfileService;
import com.Nextera.ProNetService.Service.UserService;
import com.Nextera.ProNetService.dto.Profile.ProfileHeaderDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class ProfileHeaderController {

    @Autowired
    private UserProfileService profileService;

    @Autowired
    private UserService userService;

    @PutMapping("/{id}/profile-header")
    public ResponseEntity<ProfileHeaderDto> updateProfileHeader(
            @PathVariable Integer id,
            @RequestBody ProfileHeaderDto dto
    ) {
        System.out.println(dto.getHeadLine());
        ProfileHeaderDto updated = profileService.updateHeader(id, dto);
        return ResponseEntity.ok(updated);
    }

    @GetMapping("/{id}/profile-header")
    public ResponseEntity<ProfileHeaderDto> getProfileHeader(@PathVariable Integer id) {
        // Reuse service logic to assemble a DTO-like snapshot without changing data.
        // If you prefer, add a dedicated read method in the service.
        ProfileHeaderDto snapshot = profileService.getHeader(id);
        return ResponseEntity.ok(snapshot);
    }

    @PutMapping("/{id}/profile-bio")
    public ResponseEntity<String> updateUserBio(@PathVariable Integer id, @RequestBody String bio){
        String updatedBio = profileService.updateUserBio(id, bio);
        return ResponseEntity.ok(updatedBio);
    }

    @GetMapping("/{id}/profile-bio")
    public ResponseEntity<String> getUserBio(@PathVariable Integer id){
        User currenUser = userService.getCurrentUserById(id);
        return ResponseEntity.ok(currenUser.getBio());
    }
}
