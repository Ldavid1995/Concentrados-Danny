package com.concentrados.Danny.controller;

import com.concentrados.Danny.domain.Item;
import com.concentrados.Danny.domain.Producto;
import com.concentrados.Danny.domain.Usuario;
import com.concentrados.Danny.domain.Venta;
import com.concentrados.Danny.domain.VentaDetalle;
import com.concentrados.Danny.service.ItemService;
import com.concentrados.Danny.service.ProductoService;
import com.concentrados.Danny.service.UsuarioService;
import com.concentrados.Danny.service.VentaService;
import jakarta.servlet.http.HttpSession;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import java.util.ArrayList;
import java.util.Map;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/venta")
public class VentaController {

    @Autowired
    private VentaService ventaService;
    
    @Autowired
    private UsuarioService usuarioService;
    
    @Autowired
    private ProductoService productoService;

    @PostMapping("/proceso")
    public String procesoVenta(
            @RequestParam(value = "idProducto", required = false) Long idProducto,
            @RequestParam(value = "cantidad", required = false) Integer cantidad,
            HttpSession session,
            RedirectAttributes redirectAttributes) {

        List<Item> listaDefinitiva = new ArrayList<>();

        if (idProducto != null) {
            Producto producto = productoService.getProducto(new Producto(idProducto));
            if (producto != null) {
                Item item = new Item(producto);
                item.setCantidad((cantidad != null && cantidad > 0) ? cantidad : 1);
                listaDefinitiva.add(item);
            }
        } else {
            listaDefinitiva = construirItemsDesdeSesion(session);
        }

        if (listaDefinitiva.isEmpty()) {
            redirectAttributes.addFlashAttribute("error", "El carrito está vacío.");
            return "redirect:/deseos/carrito/lista?carritoVacio=true";
        }

        try {
            Venta venta = ventaService.save(listaDefinitiva);
            limpiarSesion(session);
            redirectAttributes.addFlashAttribute("mensaje", "Compra procesada con éxito. Factura #" + venta.getIdVenta());
            return "redirect:/venta/factura/" + venta.getIdVenta();
        } catch (RuntimeException e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
            return "redirect:/deseos/carrito/lista";
        }
    }

    private void limpiarSesion(HttpSession session) {
        session.removeAttribute("carrito");
        session.removeAttribute("listaItems");
        session.removeAttribute("totalCarrito");
    }

    @SuppressWarnings("unchecked")
    private List<Item> construirItemsDesdeSesion(HttpSession session) {
        Object carrito = session.getAttribute("carrito");
        List<Item> items = new ArrayList<>();

        if (carrito instanceof Map<?, ?> carritoMap) {
            for (Map.Entry<?, ?> entry : carritoMap.entrySet()) {
                if (!(entry.getKey() instanceof Long idProducto)) {
                    continue;
                }
                int cantidad = (entry.getValue() instanceof Number n) ? n.intValue() : 1;
                if (cantidad <= 0) {
                    continue;
                }
                Producto producto = productoService.getProducto(new Producto(idProducto));
                if (producto != null) {
                    Item item = new Item(producto);
                    item.setCantidad(cantidad);
                    items.add(item);
                }
            }
        } else if (carrito instanceof List<?> carritoList) {
            for (Object obj : carritoList) {
                if (obj instanceof Item item) {
                    items.add(item);
                }
            }
        }

        return items;
    }

    @GetMapping("/historial")
    public String verHistorial(Model model) {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserDetails userDetails) {
            String username = userDetails.getUsername();
            Usuario usuario = usuarioService.getUsuarioPorUsername(username);
            
            if (usuario != null) {
                List<Venta> ventas = ventaService.getVentasPorUsuario(usuario.getIdUsuario().longValue());
                model.addAttribute("ventas", ventas);
            }
        }
        return "venta/historial";
    }

    @GetMapping("/detalle/{id}")
    public String verDetalle(@PathVariable("id") Long idVenta, Model model, RedirectAttributes redirectAttributes) {
        return "redirect:/venta/factura/" + idVenta;
    }

    @GetMapping("/factura/{id}")
    public String verFactura(@PathVariable("id") Long idVenta, Model model, RedirectAttributes redirectAttributes) {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (!(principal instanceof UserDetails userDetails)) {
            return "redirect:/login";
        }

        Usuario usuario = usuarioService.getUsuarioPorUsername(userDetails.getUsername());
        if (usuario == null) {
            redirectAttributes.addFlashAttribute("error", "No se pudo identificar el usuario actual.");
            return "redirect:/venta/historial";
        }

        Venta venta = ventaService.getVenta(idVenta);
        if (venta == null || !venta.getIdUsuario().equals(usuario.getIdUsuario().longValue())) {
            redirectAttributes.addFlashAttribute("error", "No tienes permisos para ver esa compra.");
            return "redirect:/venta/historial";
        }

        List<VentaDetalle> detalles = ventaService.getDetallesPorVenta(idVenta);
        List<LineaFactura> lineas = construirLineas(detalles);
        model.addAttribute("venta", venta);
        model.addAttribute("detalles", detalles);
        model.addAttribute("lineas", lineas);
        model.addAttribute("cliente", usuario);
        return "venta/detalle";
    }

    private List<LineaFactura> construirLineas(List<VentaDetalle> detalles) {
        List<LineaFactura> lineas = new ArrayList<>();
        for (VentaDetalle detalle : detalles) {
            Producto producto = productoService.getProducto(new Producto(detalle.getIdProducto()));
            String nombre = (producto != null) ? producto.getNombre() : ("Producto #" + detalle.getIdProducto());
            lineas.add(new LineaFactura(detalle, nombre));
        }
        return lineas;
    }

    public static class LineaFactura {
        private final VentaDetalle detalle;
        private final String nombreProducto;

        public LineaFactura(VentaDetalle detalle, String nombreProducto) {
            this.detalle = detalle;
            this.nombreProducto = nombreProducto;
        }

        public VentaDetalle getDetalle() {
            return detalle;
        }

        public String getNombreProducto() {
            return nombreProducto;
        }
    }
}