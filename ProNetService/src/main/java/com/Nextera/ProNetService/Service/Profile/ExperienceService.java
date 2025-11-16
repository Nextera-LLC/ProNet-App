package com.Nextera.ProNetService.Service.Profile;
import com.Nextera.ProNetService.Model.Profile.Experience;
import com.Nextera.ProNetService.Repository.Profile.ExperienceRepository;
import com.Nextera.ProNetService.dto.Profile.ExperienceDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
public class ExperienceService {

    @Autowired
    private ExperienceRepository repo;

    /* ---------- READ ---------- */

    public ExperienceDto getById(Integer id) {
        return repo.findById(id)
                .map(this::toDto)
                .orElseThrow(() -> new IllegalArgumentException("Experience not found: "+id));
    }

    public List<ExperienceDto> getByUser(Integer userId) {
        return repo.findByUserIdOrderByStartDateDesc(userId)
                .stream().map(this::toDto).toList();
    }

    /* ---------- CREATE ---------- */

    @Transactional
    public ExperienceDto add(ExperienceDto dto) {
        validate(dto);
        Experience entity = toEntity(dto);
        if (entity.isCurrent()) entity.setEndDate(null);
        return toDto(repo.save(entity));
    }

    /* ---------- UPDATE ---------- */

    @Transactional
    public ExperienceDto update(Integer id, ExperienceDto dto) {
        validate(dto);
        Experience e = repo.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Experience not found: "+id));

        e.setCompanyName(dto.getCompanyName());
        e.setTitle(dto.getTitle());
        e.setStartDate(dto.getStartDate());
        e.setCurrent(dto.isCurrent());
        e.setEndDate(dto.isCurrent() ? null : dto.getEndDate());
        e.setLocation(dto.getLocation());
        e.setDescription(dto.getDescription());

        return toDto(repo.save(e));
    }

    /* ---------- DELETE ---------- */

    @Transactional
    public void remove(Integer id) {
        if (!repo.existsById(id))
            throw new IllegalArgumentException("Experience not found: "+id);
        repo.deleteById(id);
    }

    /* ---------- Mapping ---------- */

    private ExperienceDto toDto(Experience e) {
        return ExperienceDto.builder()
                .experienceId(e.getExperienceId())
                .userId(e.getUserId())
                .companyName(e.getCompanyName())
                .title(e.getTitle())
                .startDate(e.getStartDate())
                .endDate(e.getEndDate())
                .location(e.getLocation())
                .isCurrent(e.isCurrent())
                .description(e.getDescription())
                .createdAt(e.getCreatedAt())
                .updatedAt(e.getUpdatedAt())
                .build();
    }

    private Experience toEntity(ExperienceDto d) {
        return Experience.builder()
                .experienceId(d.getExperienceId())
                .userId(d.getUserId())
                .companyName(d.getCompanyName())
                .title(d.getTitle())
                .startDate(d.getStartDate())
                .endDate(d.getEndDate())
                .location(d.getLocation())
                .isCurrent(d.isCurrent())
                .description(d.getDescription())
                .build();
    }

    /* ---------- Validation ---------- */

    private void validate(ExperienceDto d) {
        if (d.getCompanyName() == null || d.getCompanyName().trim().isEmpty())
            throw new IllegalArgumentException("companyName is required");
        if (d.getTitle() == null || d.getTitle().trim().isEmpty())
            throw new IllegalArgumentException("title is required");
        if (d.getStartDate() == null)
            throw new IllegalArgumentException("startDate is required");
        if (!d.isCurrent() && d.getEndDate() != null && d.getEndDate().isBefore(d.getStartDate())) {
            throw new IllegalArgumentException("endDate cannot be before startDate");
        }
    }
}