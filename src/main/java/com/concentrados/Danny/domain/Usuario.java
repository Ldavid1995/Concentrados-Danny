package com.concentrados.Danny.domain;

import jakarta.persistence.*;
import java.io.Serializable;
import java.util.List;
import lombok.Data;

@Data
@Entity
@Table(name = "usuario")
public class Usuario implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_usuario")
    private Integer idUsuario;
    
    private String username;
    private String password;
    private String nombre;
    private String apellidos;
    private String correo;
    
    // Si en tu script de BD agregaste estas columnas, déjalas. 
    // Si no las agregaste, coméntalas para evitar errores de Hibernate.
    private String telefono;
    
    @Column(name = "ruta_imagen")
    private String rutaImagen;
    
    private boolean activo;

    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_usuario") 
    private List<Rol> roles;
}