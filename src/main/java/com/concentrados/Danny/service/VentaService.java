package com.concentrados.Danny.service;

import com.concentrados.Danny.domain.Factura;
import com.concentrados.Danny.domain.Item;
import java.util.List;

public interface VentaService {

    Factura procesarVenta(Long idUsuario, List<Item> items);

    List<Factura> obtenerFacturasPorUsuario(Long idUsuario);

    Factura obtenerFacturaPorId(Long idFactura);
}