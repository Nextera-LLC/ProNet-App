package com.Nextera.ProNetService.Controller.Profile;

import com.Nextera.ProNetService.Service.Profile.ExperienceService;
import com.Nextera.ProNetService.dto.Profile.ExperienceDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/users/experiences")
@CrossOrigin // adjust origins as needed
public class ExperienceController {

    @Autowired
    private ExperienceService service;

    /**
     * List experiences for a user (most recent first)
     * GET /api/experiences?userId=123
     */
    @GetMapping
    public List<ExperienceDto> listByUser(@RequestParam Integer userId) {
        return service.getByUser(userId);
    }

    /**
     * Get one experience by id
     * GET /api/experiences/{id}
     */
    @GetMapping("/{id}")
    public ExperienceDto getById(@PathVariable Integer id) {
        return service.getById(id);
    }

    /**
     * Create a new experience
     * POST /api/experiences
     */
    @PostMapping
    public ResponseEntity<ExperienceDto> add(@Valid @RequestBody ExperienceDto request) {
        ExperienceDto created = service.add(request);
        return ResponseEntity
                .created(URI.create("/users/experiences/" + created.getExperienceId()))
                .body(created);
    }

    /**
     * Update an existing experience
     * PUT /api/experiences/{id}
     */
    @PutMapping("/{id}")
    public ExperienceDto update(@PathVariable Integer id,
                                @Valid @RequestBody ExperienceDto request) {
        return service.update(id, request);
    }

    /**
     * Delete an experience
     * DELETE /api/experiences/{id}
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> remove(@PathVariable Integer id) {
        service.remove(id);
        return ResponseEntity.noContent().build();
    }
}

