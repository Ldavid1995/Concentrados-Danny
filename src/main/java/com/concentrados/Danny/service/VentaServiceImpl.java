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
            throw new RuntimeException("El carrito está vacío.");
        }

        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (!(principal instanceof UserDetails userDetails)) {
            throw new RuntimeException("Debe iniciar sesión.");
        }

        Usuario usuario = usuarioService.getUsuarioPorUsername(userDetails.getUsername());
        
        Venta venta = new Venta();
        venta.setIdUsuario(usuario.getIdUsuario().longValue());
        venta.setFecha(new Date());
        
        double total = 0;
        for (Item item : lista) {
            total += item.getPrecio() * item.getCantidad();
        }
        venta.setTotal(total);
        venta = ventaRepository.save(venta); 

        for (Item item : lista) {
            Producto producto = productoRepository.findById(item.getIdProducto()).orElse(null);
            
            VentaDetalle detalle = new VentaDetalle();
            detalle.setIdVenta(venta.getIdVenta());
            detalle.setIdProducto(item.getIdProducto());
            detalle.setPrecio(item.getPrecio());
            detalle.setCantidad(item.getCantidad());
            ventaDetalleRepository.save(detalle);

            producto.setExistencias(producto.getExistencias() - item.getCantidad());
            productoRepository.save(producto);
        }
        return venta;
    }


    @Override
    @Transactional(readOnly = true)
    public List<Venta> obtenerVentasPorMes(int mes, int anno) {
        // Este es el que te faltaba y por eso Maven fallaba
        return ventaRepository.findVentasByMes(mes, anno);
    }

    @Override
    @Transactional(readOnly = true)
    public Double calcularTotalVentasMensuales(int mes, int anno) {
        List<Venta> ventas = ventaRepository.findVentasByMes(mes, anno);
        return ventas.stream().mapToDouble(Venta::getTotal).sum();
    }


    @Override
    @Transactional
    public Venta saveVenta(Venta venta) {
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