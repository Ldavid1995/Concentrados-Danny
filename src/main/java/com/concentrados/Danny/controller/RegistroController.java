package com.concentrados.Danny.controller;

import com.concentrados.Danny.domain.Usuario;
import com.concentrados.Danny.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/registro")
public class RegistroController {

    @Autowired
    private UsuarioService usuarioService;
    
    // 1. Pantalla de registro para nuevos clientes
    @GetMapping("/nuevo")
    public String nuevoRegistro(Model model) {
        model.addAttribute("usuario", new Usuario());
        return "registro/nuevo"; 
    }

    // 2. Procesar la creación de un nuevo usuario
    @PostMapping("/crear")
    public String crearUsuario(@ModelAttribute Usuario usuario) {
        usuario.setActivo(true);
        usuarioService.save(usuario, true); 
        return "redirect:/login?registroExitoso=true";
    }

    // 3. Procesar el cambio de rol desde la tabla de gestión
    @PostMapping("/asignarRol")
    public String asignarRol(@RequestParam("idUsuario") Integer idUsuario, 
                             @RequestParam("nombreRol") String nombreRol) {
        
        usuarioService.asignarRol(idUsuario, nombreRol);
        return "redirect:/registro/usuarios"; 
    }

    // 4. Listado de usuarios (Solo accesible para ADMIN según tu SecurityConfig)
    @GetMapping("/usuarios")
    public String listarUsuarios(Model model) {
        var usuarios = usuarioService.getUsuarios(); 
        model.addAttribute("usuarios", usuarios);
        return "registro/usuarios"; 
    }
}