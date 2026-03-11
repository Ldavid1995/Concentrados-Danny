package com.concentrados.Danny.service;

import com.concentrados.Danny.domain.Producto;
import java.util.List;

public interface ProductoService {
    

    public List<Producto> obtenerTodos();
    public List<Producto> buscarPorPalabra(String keyword);
    
    public void save(Producto producto);
    public void delete(Producto producto);
    public Producto getProducto(Producto producto);
}
