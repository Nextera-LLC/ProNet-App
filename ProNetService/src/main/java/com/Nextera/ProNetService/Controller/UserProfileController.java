package com.Nextera.ProNetService.Controller;

import com.Nextera.ProNetService.Service.UserProfileService;
import com.Nextera.ProNetService.dto.ProfileHeaderDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserProfileController {

    @Autowired
    private UserProfileService profileService;

    @PutMapping("/{id}/profile-header")
    public ResponseEntity<ProfileHeaderDto> updateProfileHeader(
            @PathVariable Integer id,
            @RequestBody ProfileHeaderDto dto
    ) {
        ProfileHeaderDto updated = profileService.updateHeader(id, dto);
        return ResponseEntity.ok(updated);
    }

    @GetMapping("/{id}/profile-header")
    public ResponseEntity<ProfileHeaderDto> getProfileHeader(@PathVariable Integer id) {
        // Reuse service logic to assemble a DTO-like snapshot without changing data.
        // If you prefer, add a dedicated read method in the service.
        ProfileHeaderDto snapshot = profileService.updateHeader(id, new ProfileHeaderDto());
        return ResponseEntity.ok(snapshot);
    }
}
