package com.concentrados.Danny.controller;

import com.concentrados.Danny.domain.Item;
import com.concentrados.Danny.domain.Producto;
import com.concentrados.Danny.service.ItemService;
import com.concentrados.Danny.service.ProductoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/carrito")
public class ItemController {

    @Autowired
    private ItemService itemService;

    @Autowired
    private ProductoService productoService;

    @GetMapping("/listado")
    public String inicio(Model model) {
        var items = itemService.getItems();
        model.addAttribute("items", items);
        model.addAttribute("totalCarrito", itemService.getTotal());
        return "/carrito/listado";
    }

    @GetMapping("/agregar/{idProducto}")
    public String agregar(@PathVariable("idProducto") Long idProducto) {
        Producto producto = productoService.obtenerPorId(idProducto);
        if (producto != null) {
            Item item = new Item(producto);
            itemService.save(item);
        }
        return "redirect:/carrito/listado";
    }

    @GetMapping("/eliminar/{idProducto}")
    public String eliminar(@PathVariable("idProducto") Long idProducto) {
        Item item = new Item();
        item.setIdProducto(idProducto);
        itemService.delete(item);
        return "redirect:/carrito/listado";
    }

    @PostMapping("/modificar")
    public String modificar(Item item) {
        itemService.update(item);
        return "redirect:/carrito/listado";
    }
}