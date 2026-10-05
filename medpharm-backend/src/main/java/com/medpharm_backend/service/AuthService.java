package com.medpharm_backend.service;

import com.medpharm_backend.dto.*;
import com.medpharm_backend.model.Usuario;
import com.medpharm_backend.repository.UsuarioRepository;
import com.medpharm_backend.security.JwtUtils;
import org.springframework.security.authentication.*;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    private final AuthenticationManager authenticationManager; private final UsuarioRepository usuarios; private final JwtUtils jwtUtils;
    public AuthService(AuthenticationManager authenticationManager, UsuarioRepository usuarios, JwtUtils jwtUtils) { this.authenticationManager = authenticationManager; this.usuarios = usuarios; this.jwtUtils = jwtUtils; }
    public AuthResponseDTO login(LoginRequestDTO request) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.username(), request.password()));
        Usuario usuario = usuarios.findByUsername(request.username()).orElseThrow();
        return new AuthResponseDTO(jwtUtils.generateToken(usuario.getUsername(), usuario.getRol()), usuario.getUsername(), usuario.getRol());
    }
}
