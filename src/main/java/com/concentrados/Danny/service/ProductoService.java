package com.concentrados.Danny.service;

import com.concentrados.Danny.domain.Producto;
import java.util.List;

public interface ProductoService {
    List<Producto> obtenerTodos();
    List<Producto> buscarPorPalabra(String keyword);
    void save(Producto producto);
    void delete(Producto producto);
    Producto getProducto(Producto producto);

    List<Object[]> obtenerStockPorMarca();
    Double calcularValorInventario(); 
}