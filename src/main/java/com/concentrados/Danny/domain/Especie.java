package com.concentrados.Danny.domain;

import jakarta.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "ESPECIE")
public class Especie implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_especie")
    @SequenceGenerator(name = "seq_especie", sequenceName = "SEQ_ESPECIE", allocationSize = 1)
    @Column(name = "ID_ESPECIE")
    private Long idEspecie;

    @Column(name = "NOMBRE", nullable = false, length = 100)
    private String nombre;

    @Column(name = "DESCRIPCION", length = 300)
    private String descripcion;

    @Column(name = "ACTIVO", nullable = false)
    private Integer activo;

    public Especie() {
    }

    public Especie(Long idEspecie) {
        this.idEspecie = idEspecie;
    }

    public Especie(String nombre, String descripcion, Integer activo) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.activo = activo;
    }

    // Getters y Setters
    public Long getIdEspecie() {
        return idEspecie;
    }

    public void setIdEspecie(Long idEspecie) {
        this.idEspecie = idEspecie;
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