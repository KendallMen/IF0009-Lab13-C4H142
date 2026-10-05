package com.medpharm_backend.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "recetas_medicas")
public class RecetaMedica {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "codigo_receta", nullable = false, unique = true) private String codigoReceta;
    @Column(name = "paciente_nombre", nullable = false) private String pacienteNombre;
    @ManyToOne(fetch = FetchType.LAZY) @JoinColumn(name = "medico_id", nullable = false) private Usuario medico;
    @Column(nullable = false) private String estado;
    @Column(name = "fecha_emision", nullable = false) private LocalDateTime fechaEmision;
    @OneToMany(mappedBy = "receta", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<DetalleReceta> detalles = new ArrayList<>();

    public Long getId() { return id; }
    public String getCodigoReceta() { return codigoReceta; }
    public void setCodigoReceta(String codigoReceta) { this.codigoReceta = codigoReceta; }
    public String getPacienteNombre() { return pacienteNombre; }
    public void setPacienteNombre(String pacienteNombre) { this.pacienteNombre = pacienteNombre; }
    public Usuario getMedico() { return medico; }
    public void setMedico(Usuario medico) { this.medico = medico; }
    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }
    public LocalDateTime getFechaEmision() { return fechaEmision; }
    public void setFechaEmision(LocalDateTime fechaEmision) { this.fechaEmision = fechaEmision; }
    public List<DetalleReceta> getDetalles() { return detalles; }
}
