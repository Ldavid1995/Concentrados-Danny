package com.concentrados.Danny.controller;

import com.concentrados.Danny.domain.Item;
import com.concentrados.Danny.domain.Producto;
import com.concentrados.Danny.service.ItemService;
import com.concentrados.Danny.service.ProductoService;
import jakarta.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/carrito") 
public class ItemController {

    @Autowired
    private ItemService itemService;

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
        
        return "carrito/lista"; 
    }

    // 2. Agregar un producto al carrito 
    @GetMapping("/agregar/{idProducto}")
    public String agregarItem(@PathVariable Long idProducto,
            @RequestParam(value = "redirect", required = false) String redirect,
            HttpSession session) {
        Map<Long, Integer> carrito = obtenerCarrito(session);
        carrito.put(idProducto, carrito.getOrDefault(idProducto, 0) + 1);
        session.setAttribute("carrito", carrito);
        return redireccionSegura(redirect);
    }

    // 3. Eliminar un producto del carrito
    @GetMapping("/eliminar/{idProducto}")
    public String eliminarItem(Item item) {
        itemService.delete(item);
        return "redirect:/carrito/listado";
    }

    // 4. Editar cantidad 
    @GetMapping("/modificar/{idProducto}")
    public String modificarItem(Item item, Model model) {
        item = itemService.get(item);
        model.addAttribute("item", item);
        return "carrito/modificar";
    }

    @SuppressWarnings("unchecked")
    private Map<Long, Integer> obtenerCarrito(HttpSession session) {
        Object valor = session.getAttribute("carrito");
        if (valor instanceof Map) {
            return (Map<Long, Integer>) valor;
        }
        Map<Long, Integer> nuevo = new HashMap<>();
        session.setAttribute("carrito", nuevo);
        return nuevo;
    }

    private String redireccionSegura(String redirect) {
        if (redirect == null || redirect.isBlank()) {
            return "redirect:/producto/listado";
        }
        if (!redirect.startsWith("/") || redirect.startsWith("//")) {
            return "redirect:/producto/listado";
        }
        return "redirect:" + redirect;
    }
}