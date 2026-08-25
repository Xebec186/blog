package com.xebec.blog.service.impl;

import com.xebec.blog.dto.AuthResponse;
import com.xebec.blog.dto.LoginRequest;
import com.xebec.blog.security.BlogUserDetails;
import com.xebec.blog.security.JwtService;
import com.xebec.blog.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final AuthenticationManager authManager;
    private final UserDetailsService userDetailsService;
    private final JwtService jwtService;

    @Override
    public AuthResponse login(LoginRequest loginRequest) {

        Authentication authentication = authManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword())
        );

        BlogUserDetails userDetails = (BlogUserDetails) authentication.getPrincipal();

        String token = jwtService.generateToken(userDetails.getUser());

        return AuthResponse.builder()
                .token(token)
                .expiresIn(jwtService.getExpirationMs())
                .build();
    }
}