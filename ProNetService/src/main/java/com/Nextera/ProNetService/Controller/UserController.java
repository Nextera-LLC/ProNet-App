package com.Nextera.ProNetService.Controller;

import com.Nextera.ProNetService.Model.User;
import com.Nextera.ProNetService.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/user")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping
    public ResponseEntity<User>  createUser(@RequestBody User user){
        User createdUser =  userService.createUser(user);
        return ResponseEntity.status(201).body(createdUser);
    }

    @GetMapping(path = "/all")
    public ResponseEntity<List<User>> getAllUsers(){
        return ResponseEntity.status(200).body(userService.getAllUsers());
    }
}
