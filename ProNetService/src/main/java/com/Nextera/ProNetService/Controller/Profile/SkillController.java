package com.Nextera.ProNetService.Controller.Profile;

import com.Nextera.ProNetService.Service.Profile.SkillService;
import com.Nextera.ProNetService.dto.Profile.SkillDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/skills")
@CrossOrigin // adjust allowed origins for your Angular app
public class SkillController {

    @Autowired
    private SkillService service;

    /**
     * List skills, optionally filtered by query:
     * - GET /api/skills
     * - GET /api/skills?query=ang
     */
    @GetMapping
    public List<SkillDto> list(@RequestParam(required = false) String query) {
        if (query == null || query.isBlank()) {
            return service.getAll();
        }
        return service.searchByName(query);
    }

    /**
     * Get single skill by id:
     * GET /api/skills/{id}
     */
    @GetMapping("/{id}")
    public SkillDto getById(@PathVariable Integer id) {
        return service.getById(id);
    }

    /**
     * Create new skill:
     * POST /api/skills
     */
    @PostMapping
    public ResponseEntity<SkillDto> add(@RequestBody SkillDto request) {
        SkillDto created = service.add(request);
        return ResponseEntity
                .created(URI.create("/api/skills/" + created.getSkillId()))
                .body(created);
    }

    /**
     * Update skill name:
     * PUT /api/skills/{id}
     */
    @PutMapping("/{id}")
    public SkillDto update(@PathVariable Integer id,
                           @RequestBody SkillDto request) {
        return service.update(id, request);
    }

    /**
     * Delete skill:
     * DELETE /api/skills/{id}
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> remove(@PathVariable Integer id) {
        service.remove(id);
        return ResponseEntity.noContent().build();
    }
}

