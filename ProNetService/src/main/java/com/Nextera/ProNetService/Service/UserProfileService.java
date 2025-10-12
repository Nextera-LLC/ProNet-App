package com.Nextera.ProNetService.Service;

import com.Nextera.ProNetService.Model.Contact;
import com.Nextera.ProNetService.Model.Location;
import com.Nextera.ProNetService.Model.User;
import com.Nextera.ProNetService.Repository.ContactRepository;
import com.Nextera.ProNetService.Repository.LocationRepository;
import com.Nextera.ProNetService.Repository.UserRepository;
import com.Nextera.ProNetService.dto.ProfileHeaderDto;
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
//          user.setFirstName(dto.getFirstName());
//         user.setLastName(dto.getLastName());
//         Update user base info
        if (dto.getFirstName() != null) user.setFirstName(dto.getFirstName());
        if (dto.getLastName() != null) user.setLastName(dto.getLastName());
//        if (dto.getHeadLine() != null) user.setHeadLine(dto.getHeadLine());
        userRepository.save(user);

        // UPSERT Location
        Location location = locationRepository.findByUser_UserId(userId)
                .orElseGet(() -> {
                    Location newLoc = new Location();
                    newLoc.setUser(user);
                    return newLoc;
                });

        if (dto.getLocation() != null) {
//            Location dtoLoc = dto.getLocation();
//            location.setCountry(dtoLoc.getCountry());
//            location.setState(dtoLoc.getState());
//            location.setCity(dtoLoc.getCity());
            Location dtoLoc = dto.getLocation();
            if (dtoLoc.getCountry() != null) location.setCountry(dtoLoc.getCountry());
            if (dtoLoc.getState() != null) location.setState(dtoLoc.getState());
            if (dtoLoc.getCity() != null) location.setCity(dtoLoc.getCity());
        }
        locationRepository.save(location);

        // UPSERT Contact
        Contact contact = contactRepository.findByUser_UserId(userId)
                .orElseGet(() -> {
                    Contact newContact = new Contact();
                    newContact.setUser(user);
                    return newContact;
                });

        if (dto.getContact() != null) {
            Contact dtoContact = dto.getContact();
//            contact.setPhone(dtoContact.getPhone());
//            contact.setEmail(dtoContact.getEmail());
//            contact.setLinkedIn(dtoContact.getLinkedIn());
            if (dtoContact.getPhone() != null) contact.setPhone(dtoContact.getPhone());
            if (dtoContact.getEmail() != null) contact.setEmail(dtoContact.getEmail());
            if (dtoContact.getLinkedIn() != null) contact.setLinkedIn(dtoContact.getLinkedIn());
        }
        contactRepository.save(contact);

        // Build response DTO (return updated state)
        ProfileHeaderDto response = new ProfileHeaderDto();
        response.setFirstName(user.getFirstName());
        response.setLastName(user.getLastName());
        response.setLocation(location);
        response.setContact(contact);

        return response;
    }
}
