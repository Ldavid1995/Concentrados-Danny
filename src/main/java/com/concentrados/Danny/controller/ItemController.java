package com.concentrados.Danny.controller;

import com.concentrados.Danny.domain.Item;
import com.concentrados.Danny.domain.Producto;
import com.concentrados.Danny.service.ItemService;
import com.concentrados.Danny.service.ProductoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/carrito") // Todas las rutas empezarán con /carrito
public class ItemController {

    @Autowired
    private ItemService itemService;

    @Autowired
    private ProductoService productoService;

    // 1. Ver el contenido del carrito
    @GetMapping("/listado")
    public String listado(Model model) {
        var items = itemService.gets();
        model.addAttribute("items", items);
        
        // Calcular el total para mostrarlo en la vista
        double carritoTotal = 0;
        for (Item i : items) {
            carritoTotal += (i.getPrecio() * i.getCantidad());
        }
        model.addAttribute("carritoTotal", carritoTotal);
        
        return "carrito/listado"; // Asegúrate de tener esta carpeta y archivo .html
    }

    // 2. Agregar un producto al carrito (EL MÁS IMPORTANTE)
    @GetMapping("/agregar/{idProducto}")
public String agregarItem(Producto producto) {
    // Buscamos la info completa (precio, nombre) del producto
    producto = productoService.getProducto(producto); 
    if (producto != null) {
        Item item = new Item(producto);
        item.setCantidad(1); // <--- IMPORTANTE: Que no inicie en 0
        itemService.save(item); // Esto lo mete en la sesión
    }
    return "redirect:/"; 
}

    // 3. Eliminar un producto del carrito
    @GetMapping("/eliminar/{idProducto}")
    public String eliminarItem(Item item) {
        itemService.delete(item);
        return "redirect:/carrito/listado";
    }

    // 4. Editar cantidad (opcional)
    @GetMapping("/modificar/{idProducto}")
    public String modificarItem(Item item, Model model) {
        item = itemService.get(item);
        model.addAttribute("item", item);
        return "carrito/modificar";
    }
}