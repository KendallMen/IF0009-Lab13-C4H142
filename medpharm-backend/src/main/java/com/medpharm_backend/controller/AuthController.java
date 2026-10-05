package com.medpharm_backend.controller;

import com.medpharm_backend.dto.*;
import com.medpharm_backend.service.AuthService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {
    private final AuthService authService;
    public AuthController(AuthService authService) { this.authService = authService; }
    @PostMapping("/login") public AuthResponseDTO login(@Valid @RequestBody LoginRequestDTO request) { return authService.login(request); }
}
