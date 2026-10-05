package com.medpharm_backend.controller;

import com.medpharm_backend.dto.MedicamentoDTO;
import com.medpharm_backend.repository.MedicamentoRepository;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/v1/medicamentos")
public class MedicamentoController {
    private final MedicamentoRepository repository;
    public MedicamentoController(MedicamentoRepository repository) { this.repository = repository; }
    @GetMapping public List<MedicamentoDTO> listar() { return repository.findAll().stream().map(MedicamentoDTO::from).toList(); }
}
