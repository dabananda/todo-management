package com.dabananda.todomanagement.controller;

import com.dabananda.todomanagement.dto.LoginDto;
import com.dabananda.todomanagement.dto.RegisterDto;
import com.dabananda.todomanagement.service.AuthService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin("*")
@AllArgsConstructor
@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private final AuthService authService;
    
    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody RegisterDto registerDto) {
        String response =  authService.register(registerDto);
        return ResponseEntity.ok(response);
    }
    
    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginDto loginDto) {
        String response =  authService.login(loginDto);
        return ResponseEntity.ok(response);
    }
}
