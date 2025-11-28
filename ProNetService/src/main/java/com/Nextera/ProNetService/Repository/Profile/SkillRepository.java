package com.Nextera.ProNetService.Repository.Profile;
import com.Nextera.ProNetService.Model.Profile.Skill;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SkillRepository extends JpaRepository<Skill, Integer> {

    Optional<Skill> findByNameIgnoreCase(String name);

    List<Skill> findByNameContainingIgnoreCaseOrderByNameAsc(String query);
}
