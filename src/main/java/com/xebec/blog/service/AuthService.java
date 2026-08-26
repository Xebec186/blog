package com.xebec.blog.service;

import com.xebec.blog.dto.AuthResponse;
import com.xebec.blog.dto.LoginRequest;
import com.xebec.blog.dto.SignupRequest;

public interface AuthService {
    AuthResponse login(LoginRequest loginRequest);
    void signup(SignupRequest signupRequest);
}