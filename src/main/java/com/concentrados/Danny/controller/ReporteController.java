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
        // Muestra el análisis que ya tienes de stock bajo y valor total
        model.addAttribute("stockMarcas", productoService.obtenerStockPorMarca());
        model.addAttribute("totalDinero", productoService.calcularValorInventario());
        return "producto/reportes"; 
    }

    @GetMapping("/cotizacion")
    public void generarCotizacion(HttpServletResponse response) throws IOException {
        // 1. Obtener los datos reales del carrito de Concentrados Danny
        List<Item> carrito = itemService.gets(); 
        
        // 2. Validar que el carrito no esté vacío antes de procesar
        if (carrito == null || carrito.isEmpty()) {
            response.sendRedirect("/carrito/listado?error=vacio");
            return;
        }

        // 3. Preparar los parámetros para el Jasper
        Map<String, Object> datos = new HashMap<>();
        datos.put("items", carrito); // Lista de productos
        datos.put("total", itemService.getTotal()); // El cálculo numérico de la proforma
        datos.put("empresa", "Concentrados Danny");
        
        // 4. Generar el PDF
        // IMPORTANTE: El nombre "cotizacion" debe ser igual a tu archivo en resources/reportes/cotizacion.jasper
        reporteService.generarPdf("cotizacion", datos, response);
    }
    @GetMapping("/ventas-mensuales")
    public String reporteMensual(@RequestParam(name="mes", defaultValue="4") int mes, Model model) {
        int annoActual = java.time.Year.now().getValue();

        var ventas = ventaService.obtenerVentasPorMes(mes, annoActual);
        Double totalMes = ventaService.calcularTotalVentasMensuales(mes, annoActual);

        model.addAttribute("ventas", ventas);
        model.addAttribute("totalMensual", totalMes);
        model.addAttribute("mesNombre", java.time.Month.of(mes).name());

        return "producto/reporte_ventas"; // Esta será tu nueva vista para el Admin
    }
}