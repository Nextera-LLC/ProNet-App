package com.Nextera.ProNetService.dto.Profile;

import com.Nextera.ProNetService.Model.Profile.Contact;
import com.Nextera.ProNetService.Model.Profile.Location;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProfileHeaderDto {
    private String firstName;
    private String lastName;
    private String headLine;
    private String bio;
    @JsonIgnore
    private Location location;
    @JsonIgnore
    private Contact contact;

    public ProfileHeaderDto(){
        location = new Location();
        contact = new Contact();
    }
}
