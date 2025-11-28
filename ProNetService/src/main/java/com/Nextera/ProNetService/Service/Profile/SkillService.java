package com.Nextera.ProNetService.Service.Profile;

import com.Nextera.ProNetService.Model.Profile.Skill;
import com.Nextera.ProNetService.Repository.Profile.SkillRepository;
import com.Nextera.ProNetService.dto.Profile.SkillDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class SkillService {

    @Autowired
    private SkillRepository repo;

    /* ========== READ ========== */

    public SkillDto getById(Integer id) {
        return repo.findById(id)
                .map(this::toDto)
                .orElseThrow(() -> new IllegalArgumentException("Skill not found: "+id));
    }

    public List<SkillDto> getAll() {
        return repo.findAll()
                .stream()
                .map(this::toDto)
                .toList();
    }

    public List<SkillDto> searchByName(String query) {
        return repo.findByNameContainingIgnoreCaseOrderByNameAsc(query)
                .stream()
                .map(this::toDto)
                .toList();
    }

    /* ========== CREATE ========== */

    @Transactional
    public SkillDto add(SkillDto dto) {
        validate(dto);

        // avoid duplicate names (respect UNIQUE constraint)
        repo.findByNameIgnoreCase(dto.getName().trim())
                .ifPresent(existing -> {
                    throw new IllegalArgumentException("Skill already exists: " + existing.getName());
                });

        Skill entity = toEntity(dto);
        return toDto(repo.save(entity));
    }

    /* ========== UPDATE ========== */

    @Transactional
    public SkillDto update(Integer id, SkillDto dto) {
        validate(dto);
        Skill skill = repo.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Skill not found: "+id));

        String newName = dto.getName().trim();

        // check if another skill with same name exists
        repo.findByNameIgnoreCase(newName)
                .filter(existing -> !existing.getSkillId().equals(id))
                .ifPresent(existing -> {
                    throw new IllegalArgumentException("Another skill already uses this name: " + existing.getName());
                });

        skill.setName(newName);
        return toDto(repo.save(skill));
    }

    /* ========== DELETE ========== */

    @Transactional
    public void remove(Integer id) {
        if (!repo.existsById(id)) {
            throw new IllegalArgumentException("Skill not found: "+id);
        }
        repo.deleteById(id);
    }

    /* ========== Mapping ========== */

    private SkillDto toDto(Skill s) {
        return SkillDto.builder()
                .skillId(s.getSkillId())
                .name(s.getName())
                .createdAt(s.getCreatedAt())
                .build();
    }

    private Skill toEntity(SkillDto d) {
        return Skill.builder()
                .skillId(d.getSkillId())
                .name(d.getName() != null ? d.getName().trim() : null)
                .build();
    }

    /* ========== Validation ========== */

    private void validate(SkillDto d) {
        if (d.getName() == null || d.getName().trim().isEmpty()) {
            throw new IllegalArgumentException("Skill name is required");
        }
        if (d.getName().trim().length() > 100) {
            throw new IllegalArgumentException("Skill name must be at most 100 characters");
        }
    }
}
