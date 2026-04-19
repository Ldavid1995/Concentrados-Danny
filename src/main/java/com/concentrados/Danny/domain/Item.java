package com.concentrados.Danny.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class Item extends Producto {
    private int cantidad; // Atributo propio de Item para el carrito

    // Constructor vacío (necesario para Spring/Jackson)
    public Item() {
    }

    // Constructor que recibe un Producto (esto arregla el error de compilación)
    public Item(Producto producto) {
        super.setIdProducto(producto.getIdProducto());
        super.setNombre(producto.getNombre());
        super.setPrecio(producto.getPrecio());
        super.setExistencias(producto.getExistencias());
        super.setMarca(producto.getMarca());
        super.setEspecie(producto.getEspecie());
        super.setRutaImagen(producto.getRutaImagen());
        this.cantidad = 0;
    }
}