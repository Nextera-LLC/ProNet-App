package com.Nextera.ProNetService.Controller;

import com.Nextera.ProNetService.Model.User;
import com.Nextera.ProNetService.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping(path = "/user")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping(path = "/all")
    public ResponseEntity<List<User>> getAllUsers(){
        return ResponseEntity.status(200).body(userService.getAllUsers());
    }

    @GetMapping(path = "/current")
    public ResponseEntity<User> getCurrentUserInfo(Principal principal){
        User currentUser = userService.getCurrentUserInfo(principal.getName());
        return ResponseEntity.status(200).body(currentUser);
    }
}
