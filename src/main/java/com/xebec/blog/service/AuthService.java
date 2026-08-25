package com.xebec.blog.service;

import com.xebec.blog.dto.AuthResponse;
import com.xebec.blog.dto.LoginRequest;

public interface AuthService {
    AuthResponse login(LoginRequest loginRequest);
}