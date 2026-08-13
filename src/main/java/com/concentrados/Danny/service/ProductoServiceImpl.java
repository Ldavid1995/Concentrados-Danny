package com.concentrados.Danny.service;

import com.concentrados.Danny.domain.Producto;
import com.concentrados.Danny.repository.ProductoRepository;
import java.math.BigDecimal;
import java.util.HashMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service
public class ProductoServiceImpl implements ProductoService {

    @Autowired
    private ProductoRepository productoRepository;

    @Override
    @Transactional(readOnly = true)
    public List<Producto> listarProductos() {
        return productoRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Producto obtenerPorId(Long id) {
        return productoRepository.findById(id).orElse(null);
    }

    @Override
    @Transactional
    public Long guardarProductoPLSQL(Producto producto, Long idCategoria) {
        Double precioDouble = producto.getPrecio() != null ? producto.getPrecio().doubleValue() : 0.0;
        
        return productoRepository.crearProducto(
            producto.getNombreProducto(),
            producto.getDescripcion(),
            precioDouble,
            producto.getStock(),
            idCategoria
        );
    }

    @Override
    @Transactional
    public void eliminarProducto(Long id) {
        productoRepository.deleteById(id);
    }
    @Override
@Transactional(readOnly = true)
public Map<String, Integer> obtenerStockPorMarca() {
    List<Producto> lista = productoRepository.findAll();
    Map<String, Integer> resumen = new HashMap<>();
    for (Producto p : lista) {
        String marca = (p.getMarca() != null && !p.getMarca().isBlank()) ? p.getMarca() : "Sin Marca";
        int stock = p.getStock() != null ? p.getStock() : 0;
        resumen.put(marca, resumen.getOrDefault(marca, 0) + stock);
    }
    return resumen;
}

    @Override
    public Producto getProducto(Producto Producto) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public BigDecimal calcularValorInventario() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}