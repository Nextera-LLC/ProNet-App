package com.Nextera.ProNetService.Service.Profile;


import com.Nextera.ProNetService.Model.Profile.Education;
import com.Nextera.ProNetService.Repository.Profile.EducationRepository;
import com.Nextera.ProNetService.dto.Profile.EducationDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class EducationService {

    @Autowired
    private EducationRepository repo;

    /* ---------- READ ---------- */

    public EducationDto getById(Integer id) {
        return repo.findById(id)
                .map(this::toDto)
                .orElseThrow(() -> new IllegalArgumentException("Education not found: "+id));
    }

    public List<EducationDto> getByUser(Integer userId) {
        return repo.findByUserIdOrderByStartDateDesc(userId)
                .stream()
                .map(this::toDto)
                .toList();
    }

    /* ---------- CREATE ---------- */

    @Transactional
    public EducationDto add(EducationDto dto) {
        validate(dto);
        Education entity = toEntity(dto);
        return toDto(repo.save(entity));
    }

    /* ---------- UPDATE ---------- */

    @Transactional
    public EducationDto update(Integer id, EducationDto dto) {
        validate(dto);
        Education e = repo.findById(id)
                .orElseThrow(() ->  new IllegalArgumentException("Education not found: "+id));

        e.setInstitution(dto.getInstitution());
        e.setDegree(dto.getDegree());
        e.setFieldOfStudy(dto.getFieldOfStudy());
        e.setStartDate(dto.getStartDate());
        e.setEndDate(dto.getEndDate());
        e.setGrade(dto.getGrade());
        e.setDescription(dto.getDescription());

        return toDto(repo.save(e));
    }

    /* ---------- DELETE ---------- */

    @Transactional
    public void remove(Integer id) {
        if (!repo.existsById(id)) {
            throw new IllegalArgumentException("Education not found: "+id);
        }
        repo.deleteById(id);
    }

    /* ---------- Mapping ---------- */

    private EducationDto toDto(Education e) {
        return EducationDto.builder()
                .educationId(e.getEducationId())
                .userId(e.getUserId())
                .institution(e.getInstitution())
                .degree(e.getDegree())
                .fieldOfStudy(e.getFieldOfStudy())
                .startDate(e.getStartDate())
                .endDate(e.getEndDate())
                .grade(e.getGrade())
                .description(e.getDescription())
                .createdAt(e.getCreatedAt())
                .updatedAt(e.getUpdatedAt())
                .build();
    }

    private Education toEntity(EducationDto d) {
        return Education.builder()
                .educationId(d.getEducationId())
                .userId(d.getUserId())
                .institution(d.getInstitution())
                .degree(d.getDegree())
                .fieldOfStudy(d.getFieldOfStudy())
                .startDate(d.getStartDate())
                .endDate(d.getEndDate())
                .grade(d.getGrade())
                .description(d.getDescription())
                .build();
    }

    /* ---------- Validation ---------- */

    private void validate(EducationDto d) {
        if (d.getInstitution() == null || d.getInstitution().trim().isEmpty()) {
            throw new IllegalArgumentException("institution is required");
        }
        // Optional: check date ordering if both present
        if (d.getStartDate() != null && d.getEndDate() != null
                && d.getEndDate().isBefore(d.getStartDate())) {
            throw new IllegalArgumentException("endDate cannot be before startDate");
        }
    }
}
