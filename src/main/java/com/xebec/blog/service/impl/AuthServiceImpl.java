package com.xebec.blog.service.impl;

import com.xebec.blog.dto.AuthResponse;
import com.xebec.blog.dto.LoginRequest;
import com.xebec.blog.dto.SignupRequest;
import com.xebec.blog.entity.User;
import com.xebec.blog.repository.UserRepository;
import com.xebec.blog.security.BlogUserDetails;
import com.xebec.blog.security.JwtService;
import com.xebec.blog.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final AuthenticationManager authManager;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
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

    @Override
    @Transactional
    public void signup(SignupRequest signupRequest) {
        if(!signupRequest.getPassword().equals(signupRequest.getConfirmPassword())) {
            throw new IllegalArgumentException("Passwords do not match");
        }

        if(userRepository.existsByEmail(signupRequest.getEmail())) {
            throw new IllegalStateException("Email already in use");
        }

        User user = User.builder()
                .name(signupRequest.getName())
                .email(signupRequest.getEmail())
                .password(passwordEncoder.encode(signupRequest.getPassword()))
                .build();

        userRepository.save(user);
    }
}