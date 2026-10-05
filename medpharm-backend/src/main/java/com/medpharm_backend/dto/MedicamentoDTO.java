package com.medpharm_backend.dto;

import com.medpharm_backend.model.Medicamento;
import java.math.BigDecimal;

public record MedicamentoDTO(Long id, String codigo, String nombre, Integer stock, BigDecimal precioUnitario) {
    public static MedicamentoDTO from(Medicamento m) {
        return new MedicamentoDTO(m.getId(), m.getCodigo(), m.getNombre(), m.getStock(), m.getPrecioUnitario());
    }
}
