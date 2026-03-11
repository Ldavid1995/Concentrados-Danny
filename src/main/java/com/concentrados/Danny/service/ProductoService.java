package com.concentrados.Danny.service;

import com.concentrados.Danny.domain.Producto;
import java.util.List;

public interface ProductoService {
    
    // Para obtener todos los concentrados (perros, gatos, tilapias, etc.)
    public List<Producto> getProductos();
    
    // Para obtener un producto específico (como el pollo por unidad)
    public Producto getProducto(Producto producto);
    
    // Para insertar o modificar (CRUD)
    public void save(Producto producto);
    
    // Para eliminar un producto
    public void delete(Producto producto);
}
