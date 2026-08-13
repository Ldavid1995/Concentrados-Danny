package com.concentrados.Danny.controller;

import com.concentrados.Danny.domain.Item;
import com.concentrados.Danny.domain.Producto;
import com.concentrados.Danny.service.ItemService;
import com.concentrados.Danny.service.ProductoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@Controller
@RequestMapping("/carrito")
public class ItemController {

    @Autowired
    private ItemService itemService;

    @Autowired
    private ProductoService productoService;

    // Listar productos del carrito
    @GetMapping("/listado")
    public String inicio(Model model) {
        List<Item> items = itemService.getItems();
        model.addAttribute("items", items);
        
        // Calcular el total recorriendo la lista de ítems usando BigDecimal
        BigDecimal total = BigDecimal.ZERO;
        if (items != null) {
            for (Item i : items) {
                if (i.getPrecio() != null) {
                    BigDecimal subtotalItem = BigDecimal.valueOf(i.getPrecio() * i.getCantidad());
                    total = total.add(subtotalItem);
                }
            }
        }
        model.addAttribute("totalCarrito", total);
        return "/carrito/listado";
    }

    // Método auxiliar para calcular el Subtotal usando BigDecimal
    public BigDecimal calcularSubtotal(Item item) {
        if (item != null && item.getPrecio() != null) {
            return BigDecimal.valueOf(item.getPrecio() * item.getCantidad());
        }
        return BigDecimal.ZERO;
    }

    // Agregar producto al carrito por ID
    @GetMapping("/agregar/{idProducto}")
    public String agregarItem(@PathVariable("idProducto") Long idProducto) {
        Item item = new Item();
        
        // Creamos la instancia de Producto con el ID para consultar el Servicio
        Producto p = new Producto();
        p.setIdProducto(idProducto);
        
        // Obtenemos el Producto completo desde la base de datos
        Producto producto = productoService.getProducto(p); 
        
        if (producto != null) {
            // Copiamos los atributos del producto al Item
            item.setIdProducto(producto.getIdProducto());
            item.setNombre(producto.getNombre());
            item.setDescripcion(producto.getDescripcion());
            item.setPrecio(producto.getPrecio());
            item.setCantidad(1);
            
            itemService.save(item);
        }

        return "redirect:/carrito/listado";
    }

    // Eliminar producto del carrito
    @GetMapping("/eliminar/{idProducto}")
    public String eliminarItem(@PathVariable("idProducto") Long idProducto) {
        Item item = new Item();
        item.setIdProducto(idProducto);
        itemService.delete(item);
        return "redirect:/carrito/listado";
    }

    // Actualizar cantidad desde el input del carrito
    @PostMapping("/actualizar")
    public String actualizarCantidad(@RequestParam("idProducto") Long idProducto, 
                                     @RequestParam("cantidad") int cantidad) {
        Item item = new Item();
        item.setIdProducto(idProducto);
        Item existe = itemService.getItem(item);
        
        if (existe != null) {
            existe.setCantidad(cantidad);
            itemService.save(existe);
        }
        
        return "redirect:/carrito/listado";
    }
}