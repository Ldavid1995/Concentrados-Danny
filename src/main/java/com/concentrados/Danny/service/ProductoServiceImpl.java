package com.concentrados.Danny.service.impl;

import com.concentrados.Danny.domain.Producto;
import com.concentrados.Danny.repository.ProductoRepository;
import com.concentrados.Danny.service.ProductoService;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ProductoServiceImpl implements ProductoService {

    @Autowired
    private ProductoRepository productoRepository;

    @Override
    @Transactional(readOnly = true)
    public List<Producto> obtenerTodos() { 
        return productoRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public List<Producto> buscarPorPalabra(String keyword) { 
        return productoRepository.findByKeyword(keyword);
    }

    @Override
    @Transactional
    public void save(Producto producto) {
        productoRepository.save(producto);
    }

    @Override
    @Transactional
    public void delete(Producto producto) {
        productoRepository.delete(producto);
    }

    @Override
    @Transactional(readOnly = true)
    public Producto getProducto(Producto producto) {
        return productoRepository.findById(producto.getIdProducto()).orElse(null);
    }

    // --- MÉTODOS PARA REPORTES (Nombres unificados con el Controller) ---

    @Override
    @Transactional(readOnly = true)
    public List<Object[]> obtenerStockPorMarca() {
        List<Producto> productos = productoRepository.findAll();
        
        // Agrupamos por marca y sumamos existencias usando Stream API
        return productos.stream()
            .filter(p -> p.getMarca() != null && !p.getMarca().isEmpty())
            .collect(Collectors.groupingBy(
                Producto::getMarca, 
                Collectors.summingInt(Producto::getExistencias)
            ))
            .entrySet().stream()
            .map(e -> new Object[]{e.getKey(), e.getValue()})
            .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public Double calcularValorInventario() {
        List<Producto> productos = productoRepository.findAll();
        // Suma de (Precio * Existencias) para obtener el valor real del inventario
        return productos.stream()
            .mapToDouble(p -> p.getPrecio() * p.getExistencias())
            .sum();
    }
}