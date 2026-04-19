package com.concentrados.Danny.service;

import com.concentrados.Danny.domain.Item;
import com.concentrados.Danny.domain.Venta;
import com.concentrados.Danny.domain.VentaDetalle;
import java.util.List;

public interface VentaService {
    // Obtener historial de Guatuso
    public List<Venta> getVentasPorUsuario(Long idUsuario);
    
    // Guardar la venta (Maestro)
    public Venta saveVenta(Venta venta);
    
    // Guardar el detalle (Productos individuales)
    public void saveDetalle(VentaDetalle detalle);
    public Venta getVenta(Long idVenta);
    public List<VentaDetalle> getDetallesPorVenta(Long idVenta);

    public void save(List<Item> lista);
}