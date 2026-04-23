package com.concentrados.Danny.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class LoginController {
    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/acceso_denegado")
    public String accesoDenegado() {
        return "acceso_denegado";
    }
    @PostMapping("/login")
    public String procesarLoginSimulado() {
        return "redirect:/"; 
    }
}
