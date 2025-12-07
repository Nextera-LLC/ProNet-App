package com.Nextera.ProNetService.Service;

import com.Nextera.ProNetService.dto.Profile.UserSkillDto;

import java.util.List;

public interface UserSkillService {
    List<UserSkillDto> getSkillsForUser(Integer userId);

    UserSkillDto getUserSkill(Integer userId, Integer userSkillId);

    UserSkillDto addSkillToUser(Integer userId, UserSkillDto dto);

    UserSkillDto updateUserSkill(Integer userId, Integer userSkillId, UserSkillDto dto);

    void removeUserSkill(Integer userId, Integer userSkillId);
}
