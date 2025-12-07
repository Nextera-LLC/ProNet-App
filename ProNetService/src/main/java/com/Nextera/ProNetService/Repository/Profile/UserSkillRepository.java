package com.Nextera.ProNetService.Repository.Profile;

import com.Nextera.ProNetService.Model.Profile.UserSkill;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserSkillRepository extends JpaRepository<UserSkill, Integer> {

    List<UserSkill> findByUserIdOrderByCreatedAtDesc(Integer userId);

    Optional<UserSkill> findByUserIdAndUserSkillId(Integer userId, Integer userSkillId);

    boolean existsByUserIdAndNameIgnoreCase(Integer userId, String name);
}
