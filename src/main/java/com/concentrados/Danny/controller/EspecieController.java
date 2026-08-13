package com.concentrados.Danny.controller;

import com.concentrados.Danny.domain.Especie;
import com.concentrados.Danny.service.EspecieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/especie")
public class EspecieController {

    @Autowired
    private EspecieService especieService;

    @GetMapping("/listado")
    public String inicio(Model model) {
        var especies = especieService.getEspecies(false);
        model.addAttribute("especies", especies);
        model.addAttribute("totalEspecies", especies.size());
        return "/especie/listado";
    }

    @GetMapping("/nuevo")
    public String especieNuevo(Especie especie) {
        return "/especie/modifica";
    }

    @PostMapping("/guardar")
    public String especieGuardar(Especie especie) {
        especieService.save(especie);
        return "redirect:/especie/listado";
    }

    @GetMapping("/modificar/{idEspecie}")
    public String especieModificar(Especie especie, Model model) {
        especie = especieService.getEspecie(especie);
        model.addAttribute("especie", especie);
        return "/especie/modifica";
    }

    @GetMapping("/eliminar/{idEspecie}")
    public String especieEliminar(Especie especie) {
        especieService.delete(especie);
        return "redirect:/especie/listado";
    }
}