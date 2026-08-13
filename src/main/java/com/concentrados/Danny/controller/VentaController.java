package com.concentrados.Danny.controller;

import com.concentrados.Danny.domain.Factura;
import com.concentrados.Danny.service.VentaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/venta")
public class VentaController {

    @Autowired
    private VentaService ventaService;

    @GetMapping("/detalle/{idFactura}")
    public String verDetalleVenta(@PathVariable("idFactura") Long idFactura, Model model) {
        Factura factura = ventaService.obtenerFacturaPorId(idFactura);
        model.addAttribute("factura", factura);
        return "/venta/detalle";
    }
}