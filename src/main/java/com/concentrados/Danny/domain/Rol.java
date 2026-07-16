package com.concentrados.Danny.domain;

import jakarta.persistence.*;
import java.io.Serializable;
import lombok.Data;

@Data
@Entity
@Table(name = "ROL") // Ajustado a mayúsculas para Oracle Cloud
public class Rol implements Serializable {
    
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_ROL") // Ajustado a mayúsculas
    private Long idRol; // Cambiado a Long para alinearse con Oracle NUMBER
    
    @Column(name = "NOMBRE")
    private String nombre;

    @Column(name = "ID_USUARIO") // Ajustado a mayúsculas
    private Long idUsuario; // Cambiado a Long para alinearse con Oracle NUMBER
}