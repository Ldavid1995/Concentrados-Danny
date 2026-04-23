package com.concentrados.Danny.controller;

import com.concentrados.Danny.domain.Usuario;
import com.concentrados.Danny.service.UsuarioService;
import java.security.Principal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class PerfilController {

    @Autowired
    private UsuarioService usuarioService;

    @GetMapping("/perfil")
    public String verPerfil(Model model, Principal principal) {
        String username = principal.getName();
        Usuario usuario = usuarioService.getUsuarioPorUsername(username);
        model.addAttribute("usuario", usuario);
        return "perfil/ver";
    }
    @PostMapping("/perfil/guardar")
public String guardarPerfil(@ModelAttribute Usuario usuario) {
    Usuario usuarioBD = usuarioService.getUsuarioPorUsername(usuario.getUsername());
    
    usuarioBD.setNombre(usuario.getNombre());
    usuarioBD.setApellidos(usuario.getApellidos());
    usuarioBD.setCorreo(usuario.getCorreo());
    usuarioBD.setTelefono(usuario.getTelefono());
    
    usuarioService.save(usuarioBD, false);
    
    return "redirect:/perfil?exito=true";
}
@GetMapping("/perfil/editar")
public String editarPerfil(Model model, Principal principal) {
    Usuario usuario = usuarioService.getUsuarioPorUsername(principal.getName());
    model.addAttribute("usuario", usuario);
    return "perfil/editar"; // Debe coincidir con la ruta en templates
}
}