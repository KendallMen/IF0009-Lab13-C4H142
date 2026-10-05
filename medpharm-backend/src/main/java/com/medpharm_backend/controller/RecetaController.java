package com.medpharm_backend.controller;

import com.medpharm_backend.dto.*;
import com.medpharm_backend.service.RecetaService;
import jakarta.validation.Valid;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/v1/recetas")
public class RecetaController {
    private final RecetaService service;
    public RecetaController(RecetaService service) { this.service = service; }
    @GetMapping public List<RecetaDTO> listar(@RequestParam(required = false) String estado) { return service.listar(estado); }
    @PostMapping public RecetaDTO crear(@Valid @RequestBody RecetaRequest request, Authentication auth) { return service.crear(request, auth.getName()); }
    @PatchMapping("/{id}/estado") public RecetaDTO cambiarEstado(@PathVariable Long id, @Valid @RequestBody EstadoRequest request) { return service.cambiarEstado(id, request.estado()); }
}
