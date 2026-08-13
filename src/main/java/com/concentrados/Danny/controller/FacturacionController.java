package com.concentrados.Danny.controller;

import com.concentrados.Danny.domain.Factura;
import com.concentrados.Danny.domain.Item;
import com.concentrados.Danny.service.ItemService;
import com.concentrados.Danny.service.VentaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/facturacion")
public class FacturacionController {

    @Autowired
    private ItemService itemService;

    @Autowired
    private VentaService ventaService;

    @GetMapping("/carrito")
    public String verCarrito(Model model) {
        List<Item> items = itemService.getItems();
        model.addAttribute("items", items);
        model.addAttribute("totalCarrito", itemService.getTotal());
        return "/facturacion/carrito";
    }

    @GetMapping("/procesar")
    public String procesarFactura(Model model) {
        List<Item> items = itemService.getItems();
        
        if (items.isEmpty()) {
            return "redirect:/facturacion/carrito";
        }

        // ID de usuario temporal (puedes reemplazarlo integrando Spring Security context)
        Long idUsuario = 1L;

        Factura factura = ventaService.procesarVenta(idUsuario, items);
        itemService.clear();

        model.addAttribute("factura", factura);
        return "/facturacion/confirmacion";
    }
}