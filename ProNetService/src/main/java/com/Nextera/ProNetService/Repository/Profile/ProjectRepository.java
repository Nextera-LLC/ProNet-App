package com.Nextera.ProNetService.Repository.Profile;

import com.Nextera.ProNetService.Model.Profile.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProjectRepository extends JpaRepository<Project, Integer> {

    List<Project> findByUserIdOrderByStartDateDesc(Integer userId);
}
