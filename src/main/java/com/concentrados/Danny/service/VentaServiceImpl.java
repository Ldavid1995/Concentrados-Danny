package com.concentrados.Danny.service.impl;

import com.concentrados.Danny.repository.VentaRepository;
import com.concentrados.Danny.repository.VentaDetalleRepository;
import com.concentrados.Danny.repository.ProductoRepository;
import com.concentrados.Danny.domain.Item;
import com.concentrados.Danny.domain.Producto;
import com.concentrados.Danny.domain.Usuario;
import com.concentrados.Danny.domain.Venta;
import com.concentrados.Danny.domain.VentaDetalle;
import com.concentrados.Danny.service.UsuarioService;
import com.concentrados.Danny.service.VentaService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class VentaServiceImpl implements VentaService {

    @Autowired
    private VentaRepository ventaRepository;
    
    @Autowired
    private VentaDetalleRepository ventaDetalleRepository;
    
    @Autowired
    private ProductoRepository productoRepository;
    
    @Autowired
    private UsuarioService usuarioService;

    @Override
@Transactional
public void save(List<Item> lista) {
    // 1. Obtener el usuario autenticado
    Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    String username = ((UserDetails) principal).getUsername();
    Usuario usuario = usuarioService.getUsuarioPorUsername(username);

    // 2. Crear la venta principal
    Venta venta = new Venta();
    venta.setIdUsuario(usuario.getIdUsuario().longValue());
    
    double total = 0;
    
    // 3. Cálculo del total con validación de tipo para evitar el ClassCastException
    for (Object obj : lista) {
        if (obj instanceof Item item) {
            total += item.getPrecio() * item.getCantidad();
        }
    }
    venta.setTotal(total);
    
    // Guardar la venta y recuperar el objeto con ID generado
    venta = this.saveVenta(venta); 

    // 4. Guardar los detalles
    for (Object obj : lista) {
        if (obj instanceof Item item) {
            VentaDetalle detalle = new VentaDetalle();
            detalle.setIdVenta(venta.getIdVenta());
            detalle.setIdProducto(item.getIdProducto());
            detalle.setPrecio(item.getPrecio());
            detalle.setCantidad(item.getCantidad());
            
            this.saveDetalle(detalle); 

            // Actualizar existencias en la base de datos de Danny
            Producto producto = productoRepository.findById(item.getIdProducto()).orElse(null);
            if (producto != null) {
                producto.setExistencias(producto.getExistencias() - item.getCantidad());
                productoRepository.save(producto);
            }
        }
    }
}

    @Override
    @Transactional
    public Venta saveVenta(Venta venta) {
        // Cambiado de void a Venta para cumplir con la Interfaz
        return ventaRepository.save(venta);
    }

    @Override
    @Transactional
    public void saveDetalle(VentaDetalle ventaDetalle) {
        ventaDetalleRepository.save(ventaDetalle);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Venta> getVentasPorUsuario(Long idUsuario) {
        return ventaRepository.findByIdUsuario(idUsuario);
    }

    @Override
    @Transactional(readOnly = true)
    public List<VentaDetalle> getDetallesPorVenta(Long idVenta) {
        return ventaDetalleRepository.findByIdVenta(idVenta);
    }

    @Override
    @Transactional(readOnly = true)
    public Venta getVenta(Long idVenta) {
        return ventaRepository.findById(idVenta).orElse(null);
    }
}