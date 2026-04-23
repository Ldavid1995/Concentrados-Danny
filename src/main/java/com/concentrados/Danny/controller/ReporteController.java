package com.concentrados.Danny.controller;

import com.concentrados.Danny.domain.Item;
import com.concentrados.Danny.service.ProductoService;
import com.concentrados.Danny.service.ItemService;
import com.concentrados.Danny.service.ReporteService;
import com.concentrados.Danny.service.VentaService;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/reporte") 
public class ReporteController {

    @Autowired
    private ProductoService productoService;
    
    @Autowired
    private VentaService ventaService;
    
    @Autowired
    private ItemService itemService; 

    @Autowired
    private ReporteService reporteService; 

    @GetMapping("/principal")
    public String mostrarReportes(Model model) {
        model.addAttribute("stockMarcas", productoService.obtenerStockPorMarca());
        model.addAttribute("totalDinero", productoService.calcularValorInventario());
        return "producto/reportes"; 
    }

    /**
     * Exporta la proforma actual del carrito en formato PDF.
     */
    @GetMapping("/exportar-proforma")
public void exportarProforma(HttpServletResponse response) {
    try {
        List<Item> carrito = itemService.gets();
        
        if (carrito == null || carrito.isEmpty()) {
            response.sendRedirect("/carrito/listado?error=vacio");
            return;
        }

        response.setContentType("application/pdf");
        response.setHeader("Content-Disposition", "inline; filename=Proforma_Danny.pdf");

        Map<String, Object> parametros = new HashMap<>();
        parametros.put("items", carrito);
        parametros.put("total", itemService.getTotal());
        parametros.put("empresa", "Concentrados Danny S.A.");

        reporteService.generarPdf("proforma", parametros, response);
        
        response.flushBuffer();

    } catch (Exception e) {
        System.err.println("Error al exportar: " + e.getMessage());
    }
}

    @GetMapping("/ventas-mensuales")
    public String reporteMensual(@RequestParam(name="mes", defaultValue="4") int mes, Model model) {
        int annoActual = java.time.Year.now().getValue();
        var ventas = ventaService.obtenerVentasPorMes(mes, annoActual);
        Double totalMes = ventaService.calcularTotalVentasMensuales(mes, annoActual);

        model.addAttribute("ventas", ventas);
        model.addAttribute("totalMensual", totalMes);
        model.addAttribute("mesNombre", java.time.Month.of(mes).name());

        return "producto/reporte_ventas"; 
    }
}