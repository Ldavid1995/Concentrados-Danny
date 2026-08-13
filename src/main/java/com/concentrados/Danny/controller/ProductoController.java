package com.concentrados.Danny.controller;

import com.concentrados.Danny.domain.Producto;
import com.concentrados.Danny.service.ProductoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/producto")
public class ProductoController {

    @Autowired
    private ProductoService productoService;

    // Listar productos
    @GetMapping("/listado")
    public String inicio(Model model) {
        var productos = productoService.listarProductos();
        model.addAttribute("productos", productos);
        model.addAttribute("totalProductos", productos.size());
        return "producto/listado";
    }

    // Formulario para nuevo producto
    @GetMapping("/nuevo")
    public String productoNuevo(Producto producto) {
        return "/producto/modifica";
    }

    // Guardar usando el paquete PL/SQL
    @PostMapping("/guardar")
    public String productoGuardar(Producto producto, @RequestParam("idCategoria") Long idCategoria) {
        productoService.guardarProductoPLSQL(producto, idCategoria);
        return "redirect:/producto/listado";
    }

    // Modificar producto existente
    @GetMapping("/modificar/{idProducto}")
    public String productoModificar(Producto producto, Model model) {
        producto = productoService.obtenerPorId(producto.getIdProducto());
        model.addAttribute("producto", producto);
        return "producto/modifica";
    }

    // Eliminar producto
    @GetMapping("/eliminar/{idProducto}")
    public String productoEliminar(Producto producto) {
        productoService.eliminarProducto(producto.getIdProducto());
        return "redirect:/producto/listado";
    }
}