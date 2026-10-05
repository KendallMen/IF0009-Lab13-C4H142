package com.medpharm_backend.service;

import com.medpharm_backend.dto.*;
import com.medpharm_backend.model.*;
import com.medpharm_backend.repository.*;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class RecetaService {
    private final RecetaRepository recetas; private final MedicamentoRepository medicamentos; private final UsuarioRepository usuarios;
    public RecetaService(RecetaRepository recetas, MedicamentoRepository medicamentos, UsuarioRepository usuarios) {
        this.recetas = recetas; this.medicamentos = medicamentos; this.usuarios = usuarios;
    }
    @Transactional(readOnly = true) public List<RecetaDTO> listar(String estado) {
        return (estado == null ? recetas.findAll() : recetas.findByEstadoIgnoreCase(estado)).stream().map(RecetaDTO::from).toList();
    }
    @Transactional public RecetaDTO crear(RecetaRequest request, String username) {
        Usuario medico = usuarios.findByUsername(username).orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED));
        RecetaMedica receta = new RecetaMedica(); receta.setCodigoReceta("RX-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase());
        receta.setPacienteNombre(request.pacienteNombre()); receta.setMedico(medico); receta.setEstado("PENDIENTE"); receta.setFechaEmision(LocalDateTime.now());
        for (DetalleRecetaRequest item : request.detalles()) {
            Medicamento medicamento = medicamentos.findById(item.medicamentoId()).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Medicamento no encontrado"));
            if (medicamento.getStock() < item.cantidad()) throw new ResponseStatusException(HttpStatus.CONFLICT, "Stock insuficiente para " + medicamento.getNombre());
            DetalleReceta detalle = new DetalleReceta(); detalle.setReceta(receta); detalle.setMedicamento(medicamento); detalle.setCantidad(item.cantidad()); detalle.setDosisIndicada(item.dosisIndicada()); receta.getDetalles().add(detalle);
        }
        return RecetaDTO.from(recetas.save(receta));
    }
    @Transactional public RecetaDTO cambiarEstado(Long id, String estado) {
        if (!List.of("DESPACHADA", "CANCELADA").contains(estado.toUpperCase())) throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Estado no permitido");
        RecetaMedica receta = recetas.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Receta no encontrada"));
        if ("PENDIENTE".equals(receta.getEstado()) && "DESPACHADA".equalsIgnoreCase(estado)) {
            receta.getDetalles().forEach(d -> d.getMedicamento().setStock(d.getMedicamento().getStock() - d.getCantidad()));
        }
        receta.setEstado(estado.toUpperCase()); return RecetaDTO.from(receta);
    }
}
