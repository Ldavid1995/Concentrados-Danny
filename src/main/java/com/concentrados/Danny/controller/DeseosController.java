package com.concentrados.Danny.controller;

import com.concentrados.Danny.domain.Producto;
import com.concentrados.Danny.service.ProductoService;
import jakarta.servlet.http.HttpSession;
import java.util.LinkedHashSet;
import java.util.List;
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
