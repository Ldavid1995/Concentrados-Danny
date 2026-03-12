package com.concentrados.Danny.controller;

import com.concentrados.Danny.service.ProductoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/reporte") 
public class ReporteController {

    @Autowired
    private ProductoService productoService;

    @GetMapping("/principal")
    public String mostrarReportes(Model model) {
        model.addAttribute("stockMarcas", productoService.stockPorMarca());
        model.addAttribute("totalDinero", productoService.valorTotal());
        return "producto/reportes"; 
    }
}