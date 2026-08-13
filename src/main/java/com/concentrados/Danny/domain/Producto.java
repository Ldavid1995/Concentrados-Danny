package com.concentrados.Danny.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import java.io.Serializable;
import lombok.Data;

@Data
@Entity
@Table(name = "producto")
public class Producto implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_producto")
    private Long idProducto;

    @Column(name = "nombre")
    private String nombre;

    @Column(name = "marca")
    private String marca;

    @Column(name = "descripcion")
    private String descripcion;

    @Column(name = "unidad_medida")
    private String unidadMedida;

    @Column(name = "precio")
    private Double precio;

    @Column(name = "stock")
    private Integer stock;

    @Column(name = "stock_minimo")
    private Integer stockMinimo;

    @Column(name = "activo")
    private Boolean activo;

    @ManyToOne
    @JoinColumn(name = "id_categoria", nullable = false)
    private Categoria categoria;

    // Atributos no persistentes (ignora Oracle)
    @Transient
    private String rutaImagen;

    @Transient
    private String especie;

    public Producto() {
    }

    // Métodos alias para no romper controladores viejos
    @Transient
    public String getNombreProducto() {
        return this.nombre;
    }

    @Transient
    public void setNombreProducto(String nombreProducto) {
        this.nombre = nombreProducto;
    }

    @Transient
    public String getEspecie() {
        return this.especie != null ? this.especie : this.descripcion;
    }

    @Transient
    public void setEspecie(String especie) {
        this.especie = especie;
    }
}