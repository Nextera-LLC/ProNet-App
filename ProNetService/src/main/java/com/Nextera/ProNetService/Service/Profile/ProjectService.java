package com.Nextera.ProNetService.Service.Profile;
import com.Nextera.ProNetService.Model.Profile.Project;
import com.Nextera.ProNetService.Repository.Profile.ProjectRepository;
import com.Nextera.ProNetService.dto.Profile.ProjectDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ProjectService {

    @Autowired
    private ProjectRepository repo;

    /* ========== READ ========== */

    public ProjectDto getById(Integer id) {
        return repo.findById(id)
                .map(this::toDto)
                .orElseThrow(() -> new IllegalArgumentException("Project not found: "+id));
    }

    public List<ProjectDto> getByUser(Integer userId) {
        return repo.findByUserIdOrderByStartDateDesc(userId)
                .stream()
                .map(this::toDto)
                .toList();
    }

    /* ========== CREATE ========== */

    @Transactional
    public ProjectDto add(ProjectDto dto) {
        validate(dto);
        Project entity = toEntity(dto);
        return toDto(repo.save(entity));
    }

    /* ========== UPDATE ========== */

    @Transactional
    public ProjectDto update(Integer id, ProjectDto dto) {
        validate(dto);
        Project p = repo.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Project not found: "+id));

        p.setTitle(dto.getTitle());
        p.setDescription(dto.getDescription());
        p.setUrl(dto.getUrl());
        p.setStartDate(dto.getStartDate());
        p.setEndDate(dto.getEndDate());

        return toDto(repo.save(p));
    }

    /* ========== DELETE ========== */

    @Transactional
    public void remove(Integer id) {
        if (!repo.existsById(id)) {
            throw new IllegalArgumentException("Project not found: "+id);
        }
        repo.deleteById(id);
    }

    /* ========== Mapping ========== */

    private ProjectDto toDto(Project p) {
        return ProjectDto.builder()
                .projectId(p.getProjectId())
                .userId(p.getUserId())
                .title(p.getTitle())
                .description(p.getDescription())
                .url(p.getUrl())
                .startDate(p.getStartDate())
                .endDate(p.getEndDate())
                .createdAt(p.getCreatedAt())
                .updatedAt(p.getUpdatedAt())
                .build();
    }

    private Project toEntity(ProjectDto d) {
        return Project.builder()
                .projectId(d.getProjectId())
                .userId(d.getUserId())
                .title(d.getTitle())
                .description(d.getDescription())
                .url(d.getUrl())
                .startDate(d.getStartDate())
                .endDate(d.getEndDate())
                .build();
    }

    /* ========== Validation ========== */

    private void validate(ProjectDto d) {
        if (d.getTitle() == null || d.getTitle().trim().isEmpty()) {
            throw new IllegalArgumentException("title is required");
        }
        // Date ordering check (if both provided)
        if (d.getStartDate() != null && d.getEndDate() != null
                && d.getEndDate().isBefore(d.getStartDate())) {
            throw new IllegalArgumentException("endDate cannot be before startDate");
        }
    }
}

