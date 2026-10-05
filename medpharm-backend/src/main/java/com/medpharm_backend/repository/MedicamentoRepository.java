package com.medpharm_backend.repository;

import com.medpharm_backend.model.Medicamento;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MedicamentoRepository extends JpaRepository<Medicamento, Long> { }
