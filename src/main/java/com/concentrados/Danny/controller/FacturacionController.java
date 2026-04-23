package com.concentrados.Danny.controller;

import com.concentrados.Danny.domain.Item;
import com.concentrados.Danny.domain.Producto;
import com.concentrados.Danny.service.ItemService;
import com.concentrados.Danny.service.ProductoService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/facturacion")
public class FacturacionController {

    @Autowired
    private ProductoService productoService;

    @Autowired
    private ItemService itemService;

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
                              HttpSession session, 
                              Model model) {
    Producto clave = new Producto();
    clave.setIdProducto(idProducto);
    Producto producto = productoService.getProducto(clave);

    if (producto == null) return "redirect:/producto/listado";

    Item item = new Item(); 
    item.setIdProducto(producto.getIdProducto());
    item.setNombre(producto.getNombre());
    item.setPrecio(producto.getPrecio());
    item.setCantidad(cantidad > 0 ? cantidad : 1);
    
    // GUARDADO DOBLE: En el service y manual en la sesión
    itemService.save(item); 
    session.setAttribute("carrito", itemService.gets()); 
    
    System.out.println("DEBUG: Guardado en sesión HTTP. Cantidad: " + itemService.gets().size());

    double total = item.getPrecio() * item.getCantidad();
    model.addAttribute("producto", producto);
    model.addAttribute("cantidad", item.getCantidad());
    model.addAttribute("metodoPago", metodoPago);
    model.addAttribute("total", total);
    model.addAttribute("facturado", true);

    return "facturacion/facturar";
}

    @GetMapping("/confirmacion/{idProducto}")
    public String mostrarConfirmacion(@PathVariable Long idProducto, Model model) {
        if (!model.containsAttribute("producto")) {
            Producto clave = new Producto();
            clave.setIdProducto(idProducto);
            model.addAttribute("producto", productoService.getProducto(clave));
        }
        return "facturacion/facturar";
    }
}