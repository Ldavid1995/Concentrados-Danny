package com.concentrados.Danny.service;

import com.concentrados.Danny.domain.Producto;
import java.math.BigDecimal;
import java.util.List;

public interface ProductoService {

    List<Producto> getProductos(boolean activos);

    List<Producto> listarProductos();

    Producto getProducto(Producto producto);

    Producto obtenerPorId(Long id);

    void save(Producto producto);

    void delete(Producto producto);

    void eliminarProducto(Long id);

    void guardarProductoPLSQL(Producto producto, Long idCategoria);

    BigDecimal calcularValorInventario();

    Integer obtenerStockPorMarca();

    Integer obtenerStockPorMarca(String marca);
}