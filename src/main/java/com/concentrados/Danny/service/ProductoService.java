package com.concentrados.Danny.service;

import com.concentrados.Danny.domain.Producto;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

public interface ProductoService {

    List<Producto> listarProductos();

    Producto obtenerPorId(Long id);

    Long guardarProductoPLSQL(Producto producto, Long idCategoria);

    void eliminarProducto(Long id);
    
    Producto getProducto(Producto Producto);
    
    BigDecimal calcularValorInventario();
    
    Map<String, Integer> obtenerStockPorMarca();
}