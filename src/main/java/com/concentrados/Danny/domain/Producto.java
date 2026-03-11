package com.concentrados.Danny.domain;

import jakarta.persistence.*;
import java.io.Serializable;
import lombok.Data; // Si usas Lombok, esto te ahorra los Getters/Setters

@Data
@Entity
@Table(name="producto")
public class Producto implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idProducto;
    
    private String nombre;
    private String marca;
    private String especie;
    private Double precio;
    private int existencias;
    private String rutaImagen;
    
    // AQUÍ ES DONDE VA LA NUEVA LÍNEA
    private String unidadMedida; 
    
    // Si NO tienes @Data de Lombok, aquí abajo debes dar clic derecho 
    // -> Insert Code -> Getter and Setter -> Seleccionar unidadMedida.
}