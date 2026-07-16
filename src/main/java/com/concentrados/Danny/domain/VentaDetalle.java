package com.concentrados.Danny.domain;

import jakarta.persistence.*;
import java.io.Serializable;
import lombok.Data;

@Data
@Entity
@Table(name = "VENTA_DETALLE") // Ajustado a mayúsculas para Oracle
public class VentaDetalle implements Serializable {
    
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_DETALLE") // Ajustado a mayúsculas
    private Long idDetalle;
    
    @Column(name = "ID_VENTA") // Ajustado a mayúsculas
    private Long idVenta;
    
    @Column(name = "ID_PRODUCTO") // Ajustado a mayúsculas
    private Long idProducto;
    
    @Column(name = "PRECIO")
    private Double precio;
    
    @Column(name = "CANTIDAD")
    private Integer cantidad;

    public VentaDetalle() {
    }
}