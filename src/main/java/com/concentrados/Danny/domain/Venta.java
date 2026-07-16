package com.concentrados.Danny.domain;

import jakarta.persistence.*;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;

@Data
@Entity
@Table(name = "VENTA") // Ajustado a mayúsculas para Oracle
public class Venta implements Serializable {
    
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_VENTA") // Ajustado a mayúsculas
    private Long idVenta;
    
    @Column(name = "ID_USUARIO") // Ajustado a mayúsculas
    private Long idUsuario;
    
    @Column(name = "TOTAL")
    private Double total;
    
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "FECHA") // Ajustado a mayúsculas
    private Date fecha;

    public Venta() {
    }

    // Constructor útil para el controlador
    public Venta(Long idUsuario, Double total) {
        this.idUsuario = idUsuario;
        this.total = total;
        this.fecha = new Date();
    }
    
    @PrePersist
    public void prePersist() {
        if (this.fecha == null) {
            this.fecha = new Date();
        }
    }
}