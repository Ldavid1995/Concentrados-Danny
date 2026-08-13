package com.concentrados.Danny.domain;

import jakarta.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "MARCA")
public class Marca implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_marca")
    @SequenceGenerator(name = "seq_marca", sequenceName = "SEQ_MARCA", allocationSize = 1)
    @Column(name = "ID_MARCA")
    private Long idMarca;

    @Column(name = "NOMBRE", nullable = false, length = 100)
    private String nombre;

    @Column(name = "DESCRIPCION", length = 300)
    private String descripcion;

    @Column(name = "ACTIVO", nullable = false)
    private Integer activo;

    public Marca() {
    }

    public Marca(Long idMarca) {
        this.idMarca = idMarca;
    }

    public Marca(String nombre, String descripcion, Integer activo) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.activo = activo;
    }

    // Getters y Setters
    public Long getIdMarca() {
        return idMarca;
    }

    public void setIdMarca(Long idMarca) {
        this.idMarca = idMarca;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Integer getActivo() {
        return activo;
    }

    public void setActivo(Integer activo) {
        this.activo = activo;
    }
}