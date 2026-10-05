package com.medpharm_backend.dto;

import com.medpharm_backend.model.*;
import java.time.LocalDateTime;
import java.util.List;

public record RecetaDTO(Long id, String codigoReceta, String pacienteNombre, String medico,
                        String estado, LocalDateTime fechaEmision, List<DetalleDTO> detalles) {
    public static RecetaDTO from(RecetaMedica receta) {
        return new RecetaDTO(receta.getId(), receta.getCodigoReceta(), receta.getPacienteNombre(),
            receta.getMedico().getNombreCompleto(), receta.getEstado(), receta.getFechaEmision(),
            receta.getDetalles().stream().map(DetalleDTO::from).toList());
    }
    public record DetalleDTO(Long medicamentoId, String medicamento, Integer cantidad, String dosisIndicada) {
        static DetalleDTO from(DetalleReceta d) {
            return new DetalleDTO(d.getMedicamento().getId(), d.getMedicamento().getNombre(), d.getCantidad(), d.getDosisIndicada());
        }
    }
}
