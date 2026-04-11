package com.concentrados.Danny.controller;

import com.concentrados.Danny.domain.Producto;
import com.concentrados.Danny.service.ProductoService;
import jakarta.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/deseos")
public class DeseosController {

    @Autowired
    private ProductoService productoService;

    // --- LÓGICA DE LISTA DE DESEOS ---

    @GetMapping("/lista")
    public String verLista(Model model, HttpSession session) {
        Set<Long> wishlistIds = obtenerWishlist(session);
        List<Producto> productos = wishlistIds.stream()
                .map(this::buscarProducto)
                .filter(Objects::nonNull)
                .collect(Collectors.toList());

        model.addAttribute("productos", productos);
        model.addAttribute("cantidadDeseos", productos.size());
        return "deseos/lista";
    }

    @GetMapping("/agregar/{idProducto}")
    public String agregar(@PathVariable Long idProducto,
                          @RequestParam(value = "redirect", required = false) String redirect,
                          HttpSession session) {
        Set<Long> wishlistIds = obtenerWishlist(session);
        wishlistIds.add(idProducto);
        session.setAttribute("wishlistIds", wishlistIds);
        return redireccionSegura(redirect);
    }

    @GetMapping("/quitar/{idProducto}")
    public String quitar(@PathVariable Long idProducto,
                         @RequestParam(value = "redirect", required = false) String redirect,
                         HttpSession session) {
        Set<Long> wishlistIds = obtenerWishlist(session);
        wishlistIds.remove(idProducto);
        session.setAttribute("wishlistIds", wishlistIds);
        return redireccionSegura(redirect != null ? redirect : "/deseos/lista");
    }

    // --- LÓGICA DE CARRITO (NUEVO) ---

    @GetMapping("/carrito/agregar/{idProducto}")
    public String agregarAlCarrito(@PathVariable Long idProducto, 
                                   @RequestParam(value = "redirect", required = false) String redirect,
                                   HttpSession session) {
        // 1. Obtener el carrito de la sesión (Map<ID, Cantidad>)
        Map<Long, Integer> carrito = obtenerCarrito(session);
        
        // 2. Si el producto ya existe, sumar +1, si no, poner 1
        carrito.put(idProducto, carrito.getOrDefault(idProducto, 0) + 1);
        
        // 3. Guardar en sesión
        session.setAttribute("carrito", carrito);
        
        return redireccionSegura(redirect);
    }

    // --- MÉTODOS DE APOYO (HELPERS) ---

    private Producto buscarProducto(Long idProducto) {
        Producto producto = new Producto();
        producto.setIdProducto(idProducto);
        return productoService.getProducto(producto);
    }

    @SuppressWarnings("unchecked")
    private Set<Long> obtenerWishlist(HttpSession session) {
        Object valor = session.getAttribute("wishlistIds");
        if (valor instanceof Set) {
            return (Set<Long>) valor;
        }
        Set<Long> nuevo = new LinkedHashSet<>();
        session.setAttribute("wishlistIds", nuevo);
        return nuevo;
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
    @GetMapping("/carrito/lista")
public String verCarrito(Model model, HttpSession session) {
    Map<Long, Integer> carrito = obtenerCarrito(session);
    
    // Convertimos el Map en una lista de objetos que la vista entienda
    List<ItemCarrito> items = carrito.entrySet().stream()
            .map(entry -> {
                Producto p = buscarProducto(entry.getKey());
                return new ItemCarrito(p, entry.getValue());
            })
            .filter(item -> item.getProducto() != null)
            .collect(Collectors.toList());

    double total = items.stream()
            .mapToDouble(item -> item.getProducto().getPrecio() * item.getCantidad())
            .sum();

    model.addAttribute("items", items);
    model.addAttribute("total", total);
    return "carrito/lista";
}

// Clase interna auxiliar (puedes ponerla al final del controlador)
public static class ItemCarrito {
    private Producto producto;
    private int cantidad;
    public ItemCarrito(Producto producto, int cantidad) { this.producto = producto; this.cantidad = cantidad; }
    public Producto getProducto() { return producto; }
    public int getCantidad() { return cantidad; }
}
}