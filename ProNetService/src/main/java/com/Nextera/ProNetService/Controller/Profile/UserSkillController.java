package com.Nextera.ProNetService.Controller.Profile;
import com.Nextera.ProNetService.Service.UserSkillService;
import com.Nextera.ProNetService.dto.Profile.UserSkillDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/users/{userId}/skills")
@CrossOrigin // adjust for your Angular origin
public class UserSkillController {

    @Autowired
    private UserSkillService userSkillService;

    /**
     * List all skills for a user
     * GET /api/users/{userId}/skills
     */
    @GetMapping
    public List<UserSkillDto> list(@PathVariable Integer userId) {
        return userSkillService.getSkillsForUser(userId);
    }

    /**
     * Get one user skill by id
     * GET /api/users/{userId}/skills/{userSkillId}
     */
    @GetMapping("/{userSkillId}")
    public UserSkillDto getOne(@PathVariable Integer userId,
                               @PathVariable Integer userSkillId) {
        return userSkillService.getUserSkill(userId, userSkillId);
    }

    /**
     * Add a new skill for user
     * POST /api/users/{userId}/skills
     *
     * body: { "name": "Angular", "level": "Intermediate", "yearsExperience": 2.5 }
     */
    @PostMapping
    public ResponseEntity<UserSkillDto> add(@PathVariable Integer userId,
                                            @RequestBody UserSkillDto request) {
        UserSkillDto created = userSkillService.addSkillToUser(userId, request);
        return ResponseEntity
                .created(URI.create("/api/users/" + userId + "/skills/" + created.getUserSkillId()))
                .body(created);
    }

    /**
     * Update user's skill
     * PUT /api/users/{userId}/skills/{userSkillId}
     */
    @PutMapping("/{userSkillId}")
    public UserSkillDto update(@PathVariable Integer userId,
                               @PathVariable Integer userSkillId,
                               @RequestBody UserSkillDto request) {
        return userSkillService.updateUserSkill(userId, userSkillId, request);
    }

    /**
     * Remove user's skill
     * DELETE /api/users/{userId}/skills/{userSkillId}
     */
    @DeleteMapping("/{userSkillId}")
    public ResponseEntity<Void> remove(@PathVariable Integer userId,
                                       @PathVariable Integer userSkillId) {
        userSkillService.removeUserSkill(userId, userSkillId);
        return ResponseEntity.noContent().build();
    }
}

