package com.concentrados.Danny.service;

import com.concentrados.Danny.domain.Producto;
import com.concentrados.Danny.repository.ProductoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
public class ProductoServiceImpl implements ProductoService {

    @Autowired
    private ProductoRepository productoRepository;

    @Override
    @Transactional(readOnly = true)
    public List<Producto> getProductos(boolean activos) {
        var lista = productoRepository.findAll();
        if (activos) {
            lista.removeIf(e -> !e.getActivo());
        }
        return lista;
    }

    @Override
    @Transactional(readOnly = true)
    public Producto getProducto(Producto producto) {
        return productoRepository.findById(producto.getIdProducto()).orElse(null);
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
    public Integer obtenerStockPorMarca() {
        return productoRepository.findAll().stream()
                .mapToInt(p -> p.getStock() != null ? p.getStock() : 0)
                .sum();
    }

    @Transactional(readOnly = true)
    public Integer obtenerStockPorMarca(String marca) {
        if (marca == null || marca.isBlank()) {
            return 0;
        }
        return productoRepository.findAll().stream()
                .filter(p -> p.getMarca() != null && p.getMarca().equalsIgnoreCase(marca))
                .mapToInt(p -> p.getStock() != null ? p.getStock() : 0)
                .sum();
    }

    @Override
    @Transactional(readOnly = true)
    public BigDecimal calcularValorInventario() {
        double total = productoRepository.findAll().stream()
                .filter(p -> p.getPrecio() != null && p.getStock() != null)
                .mapToDouble(p -> p.getPrecio().doubleValue() * p.getStock())
                .sum();
        return BigDecimal.valueOf(total);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Producto> listarProductos() {
        return productoRepository.findAll();
    }

    @Override
    @Transactional
    public void guardarProductoPLSQL(Producto producto, Long idCategoria) {
        // Guarda el producto usando el repositorio Spring Data JPA
        productoRepository.save(producto);
    }

    @Override
    @Transactional(readOnly = true)
    public Producto obtenerPorId(Long id) {
        return productoRepository.findById(id).orElse(null);
    }

    @Override
    public void eliminarProducto(Long id) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}