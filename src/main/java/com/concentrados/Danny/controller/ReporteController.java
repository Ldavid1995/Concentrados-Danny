package com.concentrados.Danny.controller;

import com.concentrados.Danny.domain.Producto;
import com.concentrados.Danny.service.ProductoPdfService;
import com.concentrados.Danny.service.ProductoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.ByteArrayInputStream;
import java.util.List;

@Controller
@RequestMapping("/reporte")
public class ReporteController {

    @Autowired
    private ProductoService productoService;

    @Autowired
    private ProductoPdfService productoPdfService;

    // Vista principal con resúmenes del inventario
    @GetMapping("/principal")
    public String principal(Model model) {
        model.addAttribute("totalInventario", productoService.calcularValorInventario());
        model.addAttribute("stockPorMarca", productoService.obtenerStockPorMarca());
        return "/reporte/principal";
    }

    // Descarga del PDF del inventario de productos
    @GetMapping("/productos/pdf")
    public ResponseEntity<InputStreamResource> descargarPdfProductos() {
        List<Producto> productos = productoService.listarProductos();
        ByteArrayInputStream bis = productoPdfService.generarReporteProductos(productos);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "inline; filename=reporte_productos.pdf");

        return ResponseEntity
                .ok()
                .headers(headers)
                .contentType(MediaType.APPLICATION_PDF)
                .body(new InputStreamResource(bis));
    }
}