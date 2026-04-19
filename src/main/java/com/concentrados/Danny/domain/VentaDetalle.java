package com.concentrados.Danny.domain;

import jakarta.persistence.*;
import java.io.Serializable;
import lombok.Data;

@Data
@Entity
@Table(name="venta_detalle")
public class VentaDetalle implements Serializable {
    
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id_detalle")
    private Long idDetalle;
    
    @Column(name="id_venta")
    private Long idVenta;
    
    @Column(name="id_producto")
    private Long idProducto;
    
    private Double precio;
    private Integer cantidad;

    public VentaDetalle() {
    }
}