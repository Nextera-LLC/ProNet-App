package com.Nextera.ProNetService.Repository;

import com.Nextera.ProNetService.Model.Location;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LocationRepository extends JpaRepository<Location, Integer> {
    Optional<Location> findByUser_UserId(Integer userId);
}
