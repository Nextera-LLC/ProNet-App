package com.Nextera.ProNetService.Repository.Profile;

import com.Nextera.ProNetService.Model.Profile.Location;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LocationRepository extends JpaRepository<Location, Integer> {
    Optional<Location> findByUser_UserId(Integer userId);
}
