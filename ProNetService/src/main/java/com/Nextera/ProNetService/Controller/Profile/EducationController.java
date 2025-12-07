package com.Nextera.ProNetService.Controller.Profile;
import com.Nextera.ProNetService.Service.Profile.EducationService;
import com.Nextera.ProNetService.dto.Profile.EducationDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/users/educations")   // follow experience pattern: plural
@CrossOrigin                         // adjust origins as needed
public class EducationController {

    @Autowired
    private EducationService service;

    /**
     * List education entries for a user:
     * GET /api/educations?userId=1
     */
    @GetMapping
    public List<EducationDto> listByUser(@RequestParam Integer userId) {
        return service.getByUser(userId);
    }

    /**
     * Get one education by id:
     * GET /api/educations/{id}
     */
    @GetMapping("/{id}")
    public EducationDto getById(@PathVariable Integer id) {
        return service.getById(id);
    }

    /**
     * Create new education:
     * POST /api/educations
     */
    @PostMapping
    public ResponseEntity<EducationDto> add(@RequestBody EducationDto request) {
        EducationDto created = service.add(request);
        return ResponseEntity
                .created(URI.create("/api/educations/" + created.getEducationId()))
                .body(created);
    }

    /**
     * Update education:
     * PUT /api/educations/{id}
     */
    @PutMapping("/{id}")
    public EducationDto update(@PathVariable Integer id,
                               @RequestBody EducationDto request) {
        return service.update(id, request);
    }

    /**
     * Delete education:
     * DELETE /api/educations/{id}
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> remove(@PathVariable Integer id) {
        service.remove(id);
        return ResponseEntity.noContent().build();
    }
}
