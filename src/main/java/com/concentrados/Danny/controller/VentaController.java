package com.concentrados.Danny.controller;

import com.concentrados.Danny.domain.Item;
import com.concentrados.Danny.domain.Producto;
import com.concentrados.Danny.domain.Usuario;
import com.concentrados.Danny.domain.Venta;
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

@Controller
@RequestMapping("/venta")
public class VentaController {

    @Autowired
    private VentaService ventaService;
    
    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private ItemService itemService;
    
    @Autowired
    private ProductoService productoService;

    @PostMapping("/proceso")
    public String procesoVenta(
            @RequestParam("idProducto") Long idProducto,
            @RequestParam("cantidad") int cantidad,
            HttpSession session) {
        
        // 1. Buscamos el producto real (Ahora funciona gracias a tu nuevo constructor)
        Producto producto = productoService.getProducto(new Producto(idProducto));
        
        if (producto != null) {
            // 2. Creamos el Item con la cantidad validada del formulario
            Item item = new Item(producto);
            item.setCantidad(cantidad);
            
            // 3. Llenamos el carrito en la sesión justo antes de la venta
            itemService.save(item);
            
            // 4. Obtenemos los items para la persistencia
            List<Item> listaDefinitiva = itemService.gets();
            
            // 5. Guardamos en MySQL (Venta y VentaDetalle)
            if (listaDefinitiva != null && !listaDefinitiva.isEmpty()) {
                ventaService.save(listaDefinitiva);
                System.out.println(">>> ÉXITO: Venta registrada en DB para " + producto.getNombre() + " x" + cantidad);
            }
        } else {
            System.out.println(">>> ERROR: No se encontró el producto con ID: " + idProducto);
        }

        // 6. Limpieza segura de la sesión después de facturar
        limpiarSesion(session);
        
        return "redirect:/";
    }

    private void limpiarSesion(HttpSession session) {
        session.removeAttribute("carrito");
        session.setAttribute("listaItems", 0);
        session.setAttribute("totalCarrito", 0.0);
    }

    @GetMapping("/historial")
    public String verHistorial(Model model) {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserDetails userDetails) {
            String username = userDetails.getUsername();
            Usuario usuario = usuarioService.getUsuarioPorUsername(username);
            
            if (usuario != null) {
                // Obtenemos el historial de compras del usuario actual
                List<Venta> ventas = ventaService.getVentasPorUsuario(usuario.getIdUsuario().longValue());
                model.addAttribute("ventas", ventas);
            }
        }
        return "venta/historial";
    }
}