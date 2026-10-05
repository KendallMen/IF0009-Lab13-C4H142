package com.medpharm_backend.repository;

import com.medpharm_backend.model.RecetaMedica;
import org.springframework.data.jpa.repository.*;
import java.util.List;

public interface RecetaRepository extends JpaRepository<RecetaMedica, Long> {
    List<RecetaMedica> findByEstadoIgnoreCase(String estado);
}
