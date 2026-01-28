package com.dabananda.todomanagement.service;

import com.dabananda.todomanagement.dto.JwtAuthResponse;
import com.dabananda.todomanagement.dto.LoginDto;
import com.dabananda.todomanagement.dto.RegisterDto;

public interface AuthService {
    String register(RegisterDto registerDto);
    JwtAuthResponse login(LoginDto loginDto);
}
