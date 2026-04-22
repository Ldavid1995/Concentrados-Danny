package com.concentrados.Danny.service;

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
import java.util.Date;
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
public Venta save(List<Item> lista) {
    if (lista == null || lista.isEmpty()) {
        throw new RuntimeException("El carrito está vacío para procesar la compra.");
    }

    // 1. Obtener el usuario autenticado
    Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    if (!(principal instanceof UserDetails userDetails)) {
        throw new RuntimeException("Debe iniciar sesión para completar la compra.");
    }

    String username = userDetails.getUsername();
    Usuario usuario = usuarioService.getUsuarioPorUsername(username);
    if (usuario == null) {
        throw new RuntimeException("No se encontró el usuario autenticado.");
    }

    // 2. Crear la venta principal
    Venta venta = new Venta();
    venta.setIdUsuario(usuario.getIdUsuario().longValue());
    venta.setFecha(new Date());
    
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
            Producto producto = productoRepository.findById(item.getIdProducto()).orElse(null);
            if (producto == null) {
                throw new RuntimeException("Producto no encontrado (ID: " + item.getIdProducto() + ").");
            }

            if (item.getCantidad() <= 0) {
                throw new RuntimeException("Cantidad inválida para el producto: " + producto.getNombre());
            }

            if (item.getCantidad() > producto.getExistencias()) {
                throw new RuntimeException(
                        "Stock insuficiente para '" + producto.getNombre() + "'. Disponible: "
                                + producto.getExistencias() + ", solicitado: " + item.getCantidad());
            }

            VentaDetalle detalle = new VentaDetalle();
            detalle.setIdVenta(venta.getIdVenta());
            detalle.setIdProducto(item.getIdProducto());
            detalle.setPrecio(item.getPrecio());
            detalle.setCantidad(item.getCantidad());
            
            this.saveDetalle(detalle);

            // Actualizar existencias en la base de datos de Danny
            producto.setExistencias(producto.getExistencias() - item.getCantidad());
            productoRepository.save(producto);
        }
    }
    return venta;
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
    @Transactional
    public List<Venta> getVentasPorUsuario(Long idUsuario) {
        repararFechasNulas();
        return ventaRepository.findByIdUsuario(idUsuario);
    }

    @Override
    @Transactional(readOnly = true)
    public List<VentaDetalle> getDetallesPorVenta(Long idVenta) {
        return ventaDetalleRepository.findByIdVenta(idVenta);
    }

    @Override
    @Transactional
    public Venta getVenta(Long idVenta) {
        repararFechasNulas();
        return ventaRepository.findById(idVenta).orElse(null);
    }

    @Transactional
    protected void repararFechasNulas() {
        ventaRepository.repararFechasNulas();
    }
}