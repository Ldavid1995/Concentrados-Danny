package com.concentrados.Danny.controller;

import com.concentrados.Danny.domain.Producto;
import com.concentrados.Danny.service.ProductoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param; 
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping; 
import org.springframework.web.bind.annotation.RequestMapping;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/producto")
public class ProductoController {

    @Autowired
    private ProductoService productoService;

    @GetMapping("/listado")
        public String listarProductos(@Param("keyword") String keyword,@Param("especie") String especie,Model model) {
            List<Producto> productos;

            if (keyword != null && !keyword.isEmpty()) {
                productos = productoService.buscarPorPalabra(keyword);
            } else {
                productos = productoService.obtenerTodos();
            }
            
            if (especie != null && !especie.isEmpty()) {
        String especieFiltro = especie.trim();
        productos = productos.stream()
                .filter(p -> p.getEspecie() != null && p.getEspecie().equalsIgnoreCase(especieFiltro))
                .collect(Collectors.toList());
    }

            model.addAttribute("productos", productos);
            model.addAttribute("keyword", keyword); // Esto mantiene el texto en la barra de búsqueda
            model.addAttribute("especieSeleccionada", especie);
            return "producto/listado";
        }


    @PostMapping("/guardar")
    public String guardar(Producto producto) {
        productoService.save(producto);
        return "redirect:/producto/listado";
    }

    @GetMapping("/eliminar/{idProducto}")
    public String eliminar(Producto producto) {
        productoService.delete(producto);
        return "redirect:/producto/listado";
    }
    
    @GetMapping("/modificar/{idProducto}")
    public String modificar(Producto producto, Model model) {
    producto = productoService.getProducto(producto); 
    model.addAttribute("producto", producto);
    return "/producto/modifica"; 
}
}