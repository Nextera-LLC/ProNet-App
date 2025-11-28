package com.Nextera.ProNetService.Controller.Profile;
import com.Nextera.ProNetService.Service.Profile.ProjectService;
import com.Nextera.ProNetService.dto.Profile.ProjectDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/users/projects")
@CrossOrigin // adjust origins as needed
public class ProjectController {

    @Autowired
    private ProjectService service;

    /**
     * List projects for a user:
     * GET /api/projects?userId=1
     */
    @GetMapping
    public List<ProjectDto> listByUser(@RequestParam Integer userId) {
        return service.getByUser(userId);
    }

    /**
     * Get one project:
     * GET /api/projects/{id}
     */
    @GetMapping("/{id}")
    public ProjectDto getById(@PathVariable Integer id) {
        return service.getById(id);
    }

    /**
     * Create a new project:
     * POST /api/projects
     */
    @PostMapping
    public ResponseEntity<ProjectDto> add(@RequestBody ProjectDto request) {
        ProjectDto created = service.add(request);
        return ResponseEntity
                .created(URI.create("/api/projects/" + created.getProjectId()))
                .body(created);
    }

    /**
     * Update a project:
     * PUT /api/projects/{id}
     */
    @PutMapping("/{id}")
    public ProjectDto update(@PathVariable Integer id,
                             @RequestBody ProjectDto request) {
        return service.update(id, request);
    }

    /**
     * Delete a project:
     * DELETE /api/projects/{id}
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> remove(@PathVariable Integer id) {
        service.remove(id);
        return ResponseEntity.noContent().build();
    }
}

