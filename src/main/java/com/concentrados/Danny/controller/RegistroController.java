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

@Controller
@RequestMapping("/registro")
public class RegistroController {

    @Autowired
    private UsuarioService usuarioService;
    
    @GetMapping("/nuevo")
    public String nuevoRegistro(Model model) {
        model.addAttribute("usuario", new Usuario());
        return "/registro/nuevo"; 
    }

    @PostMapping("/crear")
        public String crearUsuario(@ModelAttribute Usuario usuario) {
            usuario.setActivo(true);
            // Por ahora lo guardamos tal cual, luego activamos el PasswordEncoder
            usuarioService.save(usuario, true); 
            return "redirect:/login?registroExitoso=true";
        }
}