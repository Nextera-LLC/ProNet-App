package com.Nextera.ProNetService.Controller;

import com.Nextera.ProNetService.Service.AuthService;
import com.Nextera.ProNetService.dto.LoginRequest;
import com.Nextera.ProNetService.dto.RegisterRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/auth")
public class AuthController {
    @Autowired private AuthService authService;

   @PostMapping("/register")
   public ResponseEntity<String> register (@RequestBody RegisterRequest request){
       return ResponseEntity.ok(authService.register(request));
   }

   @PostMapping("/login")
   public ResponseEntity<String> login(@RequestBody LoginRequest request){
       return ResponseEntity.ok(authService.login(request));
   }
}
