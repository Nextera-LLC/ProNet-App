package com.Nextera.ProNetService.Repository.Profile;

import com.Nextera.ProNetService.Model.Profile.Experience;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ExperienceRepository extends JpaRepository<Experience,Integer> {
    List<Experience> findByUserIdOrderByStartDateDesc(Integer userId);

}
