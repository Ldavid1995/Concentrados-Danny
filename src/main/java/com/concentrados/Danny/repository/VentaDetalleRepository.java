package com.concentrados.Danny.repository;

import com.concentrados.Danny.domain.VentaDetalle;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface VentaDetalleRepository extends JpaRepository<VentaDetalle, Long> {
    // Esto permite buscar todos los productos que pertenecen a una misma factura
    List<VentaDetalle> findByIdVenta(Long idVenta);
}