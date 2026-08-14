package com.concentrados.Danny.controller;

import com.concentrados.Danny.domain.Categoria;
import com.concentrados.Danny.domain.Producto;
import com.concentrados.Danny.service.CategoriaService;
import com.concentrados.Danny.service.ProductoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/producto")
public class ProductoController {

    @Autowired
    private ProductoService productoService;

    @Autowired
    private CategoriaService categoriaService;

    @GetMapping("/listado")
    public String listado(Model model) {
        var productos = productoService.getProductos(false);
        var categorias = categoriaService.getCategorias(false);
        model.addAttribute("productos", productos);
        model.addAttribute("categorias", categorias);
        model.addAttribute("totalProductos", productos.size());
        model.addAttribute("producto", new Producto());
        return "producto/listado";
    }

    @PostMapping("/guardar")
    public String productoGuardar(@ModelAttribute("producto") Producto producto) {
        Long idCategoria = null;
        if (producto.getCategoria() != null) {
            idCategoria = producto.getCategoria().getIdCategoria();
        }

        productoService.guardarProductoPLSQL(producto, idCategoria);
        return "redirect:/producto/listado";
    }

    @GetMapping("/eliminar/{idProducto}")
    public String productoEliminar(Producto producto) {
        productoService.delete(producto);
        return "redirect:/producto/listado";
    }

    @GetMapping("/modificar/{idProducto}")
    public String productoModificar(Producto producto, Model model) {
        producto = productoService.getProducto(producto);
        
        
        if (producto.getCategoria() == null) {
            producto.setCategoria(new Categoria());
        }
        
        var categorias = categoriaService.getCategorias(false);
        model.addAttribute("producto", producto);
        model.addAttribute("categorias", categorias);
        return "producto/modifica";
    }
}