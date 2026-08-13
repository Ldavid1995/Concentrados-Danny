package com.concentrados.Danny.domain;

import java.io.Serializable;
import java.math.BigDecimal;

public class Item extends Producto implements Serializable {

    private static final long serialVersionUID = 1L;

    private int cantidad;

    public Item() {
    }

    public Item(Producto producto) {
        super.setIdProducto(producto.getIdProducto());
        super.setNombreProducto(producto.getNombreProducto());
        super.setDescripcion(producto.getDescripcion());
        super.setPrecio(producto.getPrecio());
        super.setStock(producto.getStock());
        super.setActivo(producto.getActivo());
        super.setMarca(producto.getMarca());
        super.setEspecie(producto.getEspecie());
        super.setUnidadMedida(producto.getUnidadMedida());
        super.setRutaImagen(producto.getRutaImagen());
        this.cantidad = 0;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    // Subtotal calculado para evitar errores de operadores entre BigDecimal e int
    public BigDecimal getSubTotal() {
        if (getPrecio() == null) {
            return BigDecimal.ZERO;
        }
        return getPrecio().multiply(BigDecimal.valueOf(cantidad));
    }
}