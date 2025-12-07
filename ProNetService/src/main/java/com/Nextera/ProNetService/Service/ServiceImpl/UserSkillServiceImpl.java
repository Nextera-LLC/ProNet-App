package com.Nextera.ProNetService.Service.ServiceImpl;

import com.Nextera.ProNetService.Model.Profile.UserSkill;
import com.Nextera.ProNetService.Repository.Profile.UserSkillRepository;
import com.Nextera.ProNetService.Service.UserSkillService;
import com.Nextera.ProNetService.dto.Profile.UserSkillDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UserSkillServiceImpl implements UserSkillService {

    @Autowired
    private UserSkillRepository repo;

    /* ========== READ ========== */

    @Override
    public List<UserSkillDto> getSkillsForUser(Integer userId) {
        return repo.findByUserIdOrderByCreatedAtDesc(userId)
                .stream()
                .map(this::toDto)
                .toList();
    }

    @Override
    public UserSkillDto getUserSkill(Integer userId, Integer userSkillId) {
        UserSkill us = repo.findByUserIdAndUserSkillId(userId, userSkillId)
                .orElseThrow(() -> new IllegalArgumentException(
                        "User skill not found for userId=" + userId + " and userSkillId=" + userSkillId
                ));
        return toDto(us);
    }

    /* ========== CREATE ========== */

    @Override
    @Transactional
    public UserSkillDto addSkillToUser(Integer userId, UserSkillDto dto) {
        validate(dto);

        // prevent duplicates (user_id + name)
        if (repo.existsByUserIdAndNameIgnoreCase(userId, dto.getName().trim())) {
            throw new IllegalArgumentException("User already has this skill: " + dto.getName());
        }

        UserSkill us = toEntity(dto);
        us.setUserId(userId); // enforce path variable as source of truth

        return toDto(repo.save(us));
    }

    /* ========== UPDATE ========== */

    @Override
    @Transactional
    public UserSkillDto updateUserSkill(Integer userId, Integer userSkillId, UserSkillDto dto) {
        validate(dto);

        UserSkill existing = repo.findByUserIdAndUserSkillId(userId, userSkillId)
                .orElseThrow(() -> new IllegalArgumentException(
                        "User skill not found for userId=" + userId + " and userSkillId=" + userSkillId
                ));

        String newName = dto.getName().trim();

        // If name changed, ensure no duplicate for this user
        if (!existing.getName().equalsIgnoreCase(newName)
                && repo.existsByUserIdAndNameIgnoreCase(userId, newName)) {
            throw new IllegalArgumentException("User already has a skill with name: " + newName);
        }

        existing.setName(newName);
        existing.setLevel(dto.getLevel());
        existing.setYearsExperience(dto.getYearsExperience());

        return toDto(repo.save(existing));
    }

    /* ========== DELETE ========== */

    @Override
    @Transactional
    public void removeUserSkill(Integer userId, Integer userSkillId) {
        UserSkill existing = repo.findByUserIdAndUserSkillId(userId, userSkillId)
                .orElseThrow(() -> new IllegalArgumentException(
                        "User skill not found for userId=" + userId + " and userSkillId=" + userSkillId
                ));
        repo.delete(existing);
    }

    /* ========== Mapping ========== */

    private UserSkillDto toDto(UserSkill us) {
        return UserSkillDto.builder()
                .userSkillId(us.getUserSkillId())
                .userId(us.getUserId())
                .name(us.getName())
                .level(us.getLevel())
                .yearsExperience(us.getYearsExperience())
                .createdAt(us.getCreatedAt())
                .updatedAt(us.getUpdatedAt())
                .build();
    }

    private UserSkill toEntity(UserSkillDto d) {
        return UserSkill.builder()
                .userSkillId(d.getUserSkillId())
                .userId(d.getUserId()) // will be overridden in addSkillToUser
                .name(d.getName() != null ? d.getName().trim() : null)
                .level(d.getLevel())
                .yearsExperience(d.getYearsExperience())
                .build();
    }

    /* ========== Validation ========== */

    private void validate(UserSkillDto d) {
        if (d.getName() == null || d.getName().trim().isEmpty()) {
            throw new IllegalArgumentException("Skill name is required");
        }
        if (d.getName().trim().length() > 100) {
            throw new IllegalArgumentException("Skill name must be at most 100 characters");
        }
        if (d.getLevel() != null && d.getLevel().length() > 50) {
            throw new IllegalArgumentException("Level must be at most 50 characters");
        }
    }
}
