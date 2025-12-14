package com.Nextera.ProNetService.Service.Profile;

import com.Nextera.ProNetService.Model.Profile.Contact;
import com.Nextera.ProNetService.Model.Profile.Location;
import com.Nextera.ProNetService.Model.User;
import com.Nextera.ProNetService.Repository.Profile.ContactRepository;
import com.Nextera.ProNetService.Repository.Profile.LocationRepository;
import com.Nextera.ProNetService.Repository.UserRepository;
import com.Nextera.ProNetService.dto.Profile.ProfileHeaderDto;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserProfileService {
    @Autowired
    private ContactRepository contactRepository;
    @Autowired
    private LocationRepository locationRepository;
    @Autowired
    private UserRepository userRepository;

    @Transactional
    public ProfileHeaderDto updateHeader(Integer userId, ProfileHeaderDto dto) {

        // Find user
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found: " + userId));
//         Update user base info
        if (dto.getFirstName() != null) user.setFirstName(dto.getFirstName());
        if (dto.getLastName() != null) user.setLastName(dto.getLastName());
        if (dto.getHeadLine() != null) user.setHeadline(dto.getHeadLine());
        userRepository.save(user);

        // UPSERT Location
        Location location = locationRepository.findByUser_UserId(userId)
                .orElseGet(() -> {
                    Location newLoc = new Location();
                    newLoc.setUser(user);
                    return newLoc;
                });

        if (dto.getLocation() != null) {
            Location dtoLoc = dto.getLocation();
            if (dtoLoc.getCountry() != null) location.setCountry(dtoLoc.getCountry());
            if (dtoLoc.getState() != null) location.setState(dtoLoc.getState());
            if (dtoLoc.getCity() != null) location.setCity(dtoLoc.getCity());
            locationRepository.save(location);
        }


        // UPSERT Contact
        Contact contact = contactRepository.findByUser_UserId(userId)
                .orElseGet(() -> {
                    Contact newContact = new Contact();
                    newContact.setUser(user);
                    return newContact;
                });

        if (dto.getContact() != null) {
            Contact dtoContact = dto.getContact();
            if (dtoContact.getPhone() != null) contact.setPhone(dtoContact.getPhone());
            if (dtoContact.getEmail() != null) contact.setEmail(dtoContact.getEmail());
            if (dtoContact.getLinkedIn() != null) contact.setLinkedIn(dtoContact.getLinkedIn());
            contactRepository.save(contact);

        }

        // Build response DTO (return updated state)
        ProfileHeaderDto response = new ProfileHeaderDto();
        response.setFirstName(user.getFirstName());
        response.setLastName(user.getLastName());
        response.setLocation(location);
        response.setContact(contact);
        response.setHeadLine(user.getHeadline());
        response.setBio(user.getBio());

        return response;
    }
public ProfileHeaderDto getHeader(Integer userId){
    // Find user
    User user = userRepository.findById(userId)
            .orElseThrow(() -> new IllegalArgumentException("User not found: " + userId));

    Location location = locationRepository.findByUser_UserId(userId)
            .orElseGet(() -> {
                Location newLoc = new Location();
                newLoc.setUser(user);
                return newLoc;
            });

    Contact contact = contactRepository.findByUser_UserId(userId)
            .orElseGet(() -> {
                Contact newContact = new Contact();
                newContact.setUser(user);
                return newContact;
            });


    // Build response DTO (return updated state)
    ProfileHeaderDto response = new ProfileHeaderDto();
    response.setFirstName(user.getFirstName());
    response.setLastName(user.getLastName());
    response.setLocation(location);
    response.setContact(contact);
    response.setHeadLine(user.getHeadline());
    response.setBio(user.getBio());


    return response;
    }
    @Transactional
    public String updateUserBio(Integer userId, String bio){
        // Find user
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found: " + userId));
        user.setBio(bio);

        return user.getBio();
    }
}
