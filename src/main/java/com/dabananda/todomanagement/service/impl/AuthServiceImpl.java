package com.dabananda.todomanagement.service.impl;

import com.dabananda.todomanagement.dto.JwtAuthResponse;
import com.dabananda.todomanagement.dto.LoginDto;
import com.dabananda.todomanagement.dto.RegisterDto;
import com.dabananda.todomanagement.entity.Role;
import com.dabananda.todomanagement.entity.User;
import com.dabananda.todomanagement.exception.TodoApiException;
import com.dabananda.todomanagement.repository.RoleRepository;
import com.dabananda.todomanagement.repository.UserRepository;
import com.dabananda.todomanagement.security.JwtTokenProvider;
import com.dabananda.todomanagement.service.AuthService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
@AllArgsConstructor
public class AuthServiceImpl implements AuthService {
    private UserRepository userRepository;
    private RoleRepository roleRepository;
    private PasswordEncoder passwordEncoder;
    private AuthenticationManager authenticationManager;
    private JwtTokenProvider jwtTokenProvider;
    
    @Override
    public String register(RegisterDto registerDto) {
        if (userRepository.existsByUsername(registerDto.getUsername())) {
            throw new TodoApiException(HttpStatus.BAD_REQUEST, "Username already exists");
        }
        
        if (userRepository.existsByEmail(registerDto.getEmail())) {
            throw new TodoApiException(HttpStatus.BAD_REQUEST, "Email is already in use");
        }

        User user = new User();
        user.setName(registerDto.getName());
        user.setUsername(registerDto.getUsername());
        user.setEmail(registerDto.getEmail());
        user.setPassword(passwordEncoder.encode(registerDto.getPassword()));

        Set<Role>  roles = new HashSet<>();
        Role userRole = roleRepository.findByName("ROLE_USER");
        roles.add(userRole);
        
        user.setRoles(roles);
        
        userRepository.save(user);
        
        return "User registered successfully";
    }

    @Override
    public JwtAuthResponse login(LoginDto loginDto) {
        Authentication authentication =  authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                loginDto.getUsernameOrEmail(),
                loginDto.getPassword()
        ));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String token = jwtTokenProvider.generateJwtToken(authentication);
        java.util.Optional<User> userOptional = userRepository.findByUsernameOrEmail(loginDto.getUsernameOrEmail(), loginDto.getUsernameOrEmail());
        
        String role = null;
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            Optional<Role> optionalRole = user.getRoles().stream().findFirst();
            if (optionalRole.isPresent()) {
                com.dabananda.todomanagement.entity.Role userRole = optionalRole.get();
                role = userRole.getName();
            }
        }
        
        JwtAuthResponse jwtAuthResponse = new JwtAuthResponse();
        jwtAuthResponse.setRole(role);
        jwtAuthResponse.setAccessToken(token);
        
        return jwtAuthResponse;
    }
}
