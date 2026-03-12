package com.concentrados.Danny.controller;

import com.concentrados.Danny.domain.Producto;
import com.concentrados.Danny.service.ProductoService;
import com.concentrados.Danny.service.FichaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/producto")
public class ProductoController {

    @Autowired
    private ProductoService productoService;

    @Autowired
    private FichaService fichaService;

    @GetMapping("/listado")
    public String listarProductos(@Param("keyword") String keyword, @Param("especie") String especie, Model model) {
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
        model.addAttribute("keyword", keyword);
        model.addAttribute("especieSeleccionada", especie);
        return "producto/listado";
    }

    @PostMapping("/guardar")
    public String guardarProducto(Producto producto, 
            @RequestParam(value = "archivoFicha", required = false) MultipartFile archivo) {

        if (archivo != null && !archivo.isEmpty()) {
            String nombreFinal = fichaService.guardarFicha(archivo);
            if (nombreFinal != null) {
                producto.setFichaTecnica(nombreFinal); 
            }
        }

        productoService.save(producto);
        String especieUrl = (producto.getEspecie() != null) ? producto.getEspecie() : "";
        return "redirect:/producto/listado?especie=" + especieUrl;
    }

    @GetMapping("/eliminar/{idProducto}")
    public String eliminar(Producto producto) {
        Producto p = productoService.getProducto(producto);
        String especieParaRedireccion = (p != null && p.getEspecie() != null) ? p.getEspecie() : "";
        productoService.delete(producto);
        return "redirect:/producto/listado?especie=" + especieParaRedireccion;
    }

    @GetMapping("/modificar/{idProducto}")
    public String modificar(Producto producto, Model model) {
        producto = productoService.getProducto(producto);
        model.addAttribute("producto", producto);
        return "/producto/modifica";
    }


    @GetMapping("/reportes")
    public String verReportes(Model model) {
        List<Object[]> stockMarcas = productoService.obtenerStockPorMarca();
        Double totalDinero = productoService.calcularValorInventario();

        boolean hayStockBajo = stockMarcas.stream()
                .anyMatch(fila -> ((Number) fila[1]).intValue() <= 5);

        model.addAttribute("stockMarcas", stockMarcas);
        model.addAttribute("totalDinero", totalDinero);
        model.addAttribute("hayStockBajo", hayStockBajo);

        return "producto/reportes";
    }
}