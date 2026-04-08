package com.concentrados.Danny.controller;

import com.concentrados.Danny.domain.Producto;
import com.concentrados.Danny.service.ProductoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/facturacion")
public class FacturacionController {
    @Autowired
    private ProductoService productoService;

    @GetMapping("/{idProducto}")
    public String verFacturacion(@PathVariable Long idProducto, Model model) {
        Producto clave = new Producto();
        clave.setIdProducto(idProducto);
        Producto producto = productoService.getProducto(clave);

        if (producto == null) {
            return "redirect:/producto/listado";
        }

        model.addAttribute("producto", producto);
        model.addAttribute("cantidad", 1);
        return "facturacion/facturar";
    }

    @PostMapping("/procesar")
    public String procesarFactura(@RequestParam Long idProducto,
                                  @RequestParam int cantidad,
                                  @RequestParam String metodoPago,
                                  Model model) {
        Producto clave = new Producto();
        clave.setIdProducto(idProducto);
        Producto producto = productoService.getProducto(clave);

        if (producto == null) {
            return "redirect:/producto/listado";
        }

        if (cantidad <= 0) {
            cantidad = 1;
        }

        double total = (producto.getPrecio() != null ? producto.getPrecio() : 0.0) * cantidad;

        model.addAttribute("producto", producto);
        model.addAttribute("cantidad", cantidad);
        model.addAttribute("metodoPago", metodoPago);
        model.addAttribute("total", total);
        model.addAttribute("facturado", true);

        return "facturacion/facturar";
    }
    
}
