package com.Nextera.ProNetService.dto;

import com.Nextera.ProNetService.Model.Contact;
import com.Nextera.ProNetService.Model.Location;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProfileHeaderDto {
    private String firstName;
    private String lastName;
    private String headLine;
    private String bio;
    private Location location;
    private Contact contact;

    public ProfileHeaderDto(){
        location = new Location();
        contact = new Contact();
    }
}
