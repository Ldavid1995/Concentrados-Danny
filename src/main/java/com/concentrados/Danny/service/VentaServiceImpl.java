package com.concentrados.Danny.service;

import com.concentrados.Danny.domain.Factura;
import com.concentrados.Danny.domain.Item;
import com.concentrados.Danny.domain.Producto;
import com.concentrados.Danny.domain.Venta;
import com.concentrados.Danny.repository.FacturaRepository;
import com.concentrados.Danny.repository.ProductoRepository;
import com.concentrados.Danny.repository.VentaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
public class VentaServiceImpl implements VentaService {

    @Autowired
    private FacturaRepository facturaRepository;

    @Autowired
    private VentaRepository ventaRepository;

    @Autowired
    private ProductoRepository productoRepository;

    @Override
    @Transactional
    public Factura procesarVenta(Long idUsuario, List<Item> items) {
        if (items == null || items.isEmpty()) {
            return null;
        }

        // 1. Calcular el total acumulado de los ítems
        BigDecimal totalCalculado = BigDecimal.ZERO;
        for (Item item : items) {
            if (item.getPrecio() != null) {
                // Multiplicamos precio (Double) por cantidad y lo acumulamos en BigDecimal
                BigDecimal subtotalItem = BigDecimal.valueOf(item.getPrecio() * item.getCantidad());
                totalCalculado = totalCalculado.add(subtotalItem);
            }
        }

        // 2. Crear y guardar el registro de la Factura
        Factura factura = new Factura(idUsuario, totalCalculado, 1);
        factura = facturaRepository.save(factura);

        // 3. Crear los detalles de venta y rebajar existencias en base de datos
        for (Item item : items) {
            // Conversión explícita de Double a BigDecimal para evitar error de tipos en la Venta
            BigDecimal precioBigDecimal = (item.getPrecio() != null) ? BigDecimal.valueOf(item.getPrecio()) : BigDecimal.ZERO;

            Venta ventaDetalle = new Venta(
                factura.getIdFactura(),
                item.getIdProducto(),
                precioBigDecimal,
                item.getCantidad()
            );
            ventaRepository.save(ventaDetalle);

            // Descontar inventario
            Producto producto = productoRepository.findById(item.getIdProducto()).orElse(null);
            if (producto != null && producto.getStock() != null) {
                int nuevoStock = producto.getStock() - item.getCantidad();
                producto.setStock(Math.max(nuevoStock, 0));
                productoRepository.save(producto);
            }
        }

        return factura;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Factura> obtenerFacturasPorUsuario(Long idUsuario) {
        return facturaRepository.findByIdUsuario(idUsuario);
    }

    @Override
    @Transactional(readOnly = true)
    public Factura obtenerFacturaPorId(Long idFactura) {
        return facturaRepository.findById(idFactura).orElse(null);
    }
}