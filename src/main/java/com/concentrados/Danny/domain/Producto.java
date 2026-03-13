package com.concentrados.Danny.domain;

import jakarta.persistence.*;
import java.io.Serializable;
import lombok.Data; 
import java.time.LocalDate;
import org.springframework.format.annotation.DateTimeFormat;

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
    private String fichaTecnica;

    private String unidadMedida; 
    
    private String lote;

@DateTimeFormat(pattern = "yyyy-MM-dd")
private LocalDate fechaVencimiento; 
    

}