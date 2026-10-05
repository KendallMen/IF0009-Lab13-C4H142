package com.medpharm_backend.model;

import jakarta.persistence.*;

@Entity
@Table(name = "detalles_receta")
public class DetalleReceta {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY) @JoinColumn(name = "receta_id", nullable = false) private RecetaMedica receta;
    @ManyToOne(fetch = FetchType.EAGER) @JoinColumn(name = "medicamento_id", nullable = false) private Medicamento medicamento;
    @Column(nullable = false) private Integer cantidad;
    @Column(name = "dosis_indicada", nullable = false) private String dosisIndicada;

    public Long getId() { return id; }
    public RecetaMedica getReceta() { return receta; }
    public void setReceta(RecetaMedica receta) { this.receta = receta; }
    public Medicamento getMedicamento() { return medicamento; }
    public void setMedicamento(Medicamento medicamento) { this.medicamento = medicamento; }
    public Integer getCantidad() { return cantidad; }
    public void setCantidad(Integer cantidad) { this.cantidad = cantidad; }
    public String getDosisIndicada() { return dosisIndicada; }
    public void setDosisIndicada(String dosisIndicada) { this.dosisIndicada = dosisIndicada; }
}
