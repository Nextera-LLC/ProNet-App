package com.Nextera.ProNetService.Repository.Profile;

import com.Nextera.ProNetService.Model.Profile.Contact;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ContactRepository extends JpaRepository<Contact, Integer> {
    Optional<Contact> findByUser_UserId(Integer userId);
}
