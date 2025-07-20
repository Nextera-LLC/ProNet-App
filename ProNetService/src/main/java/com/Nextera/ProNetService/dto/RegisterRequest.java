package com.Nextera.ProNetService.dto;

import lombok.Data;

@Data
public class RegisterRequest {
    private String userName;
    private String email;
    private String password;
//    private String confirmPassword;
    private String firstName;
    private  String lastName;

}
