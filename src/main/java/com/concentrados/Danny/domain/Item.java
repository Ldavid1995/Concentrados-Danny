package com.concentrados.Danny.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class Item extends Producto implements Serializable {

    private static final long serialVersionUID = 1L;

    private int cantidad;

    public Item(Producto producto) {
        super.setIdProducto(producto.getIdProducto());
        super.setCategoria(producto.getCategoria());
        super.setNombre(producto.getNombre());
        super.setMarca(producto.getMarca());
        super.setDescripcion(producto.getDescripcion());
        super.setUnidadMedida(producto.getUnidadMedida());
        super.setPrecio(producto.getPrecio());
        super.setStock(producto.getStock());
        super.setStockMinimo(producto.getStockMinimo());
        super.setEspecie(producto.getEspecie());
        super.setRutaImagen(producto.getRutaImagen());
        super.setActivo(producto.getActivo());
        this.cantidad = 0;
    }

    public double getSubTotal() {
        if (getPrecio() != null) {
            return getPrecio().doubleValue() * cantidad;
        }
        return 0.0;
    }
}