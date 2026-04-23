package com.concentrados.Danny.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class Item extends Producto {
    private int cantidad; 

    public Item() {
    }
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