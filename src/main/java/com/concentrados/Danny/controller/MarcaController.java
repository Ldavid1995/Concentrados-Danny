package com.concentrados.Danny.controller;

import com.concentrados.Danny.domain.Marca;
import com.concentrados.Danny.service.MarcaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/marca")
public class MarcaController {

    @Autowired
    private MarcaService marcaService;

    @GetMapping("/listado")
    public String inicio(Model model) {
        var marcas = marcaService.getMarcas(false);
        model.addAttribute("marcas", marcas);
        model.addAttribute("totalMarcas", marcas.size());
        return "/marca/listado";
    }

    @GetMapping("/nuevo")
    public String marcaNuevo(Marca marca) {
        return "/marca/modifica";
    }

    @PostMapping("/guardar")
    public String marcaGuardar(Marca marca) {
        marcaService.save(marca);
        return "redirect:/marca/listado";
    }

    @GetMapping("/modificar/{idMarca}")
    public String marcaModificar(Marca marca, Model model) {
        marca = marcaService.getMarca(marca);
        model.addAttribute("marca", marca);
        return "/marca/modifica";
    }

    @GetMapping("/eliminar/{idMarca}")
    public String marcaEliminar(Marca marca) {
        marcaService.delete(marca);
        return "redirect:/marca/listado";
    }
}