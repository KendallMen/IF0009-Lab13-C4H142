package com.medpharm_backend.model;

import jakarta.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "medicamentos")
public class Medicamento {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, unique = true) private String codigo;
    @Column(nullable = false) private String nombre;
    @Column(nullable = false) private Integer stock;
    @Column(name = "precio_unitario", nullable = false) private BigDecimal precioUnitario;

    public Long getId() { return id; }
    public String getCodigo() { return codigo; }
    public String getNombre() { return nombre; }
    public Integer getStock() { return stock; }
    public void setStock(Integer stock) { this.stock = stock; }
    public BigDecimal getPrecioUnitario() { return precioUnitario; }
}
