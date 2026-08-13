package com.concentrados.Danny;

import com.concentrados.Danny.service.PlsqlService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class DannyApplication implements CommandLineRunner {

    @Autowired
    private PlsqlService plsqlService;

    public static void main(String[] args) {
        SpringApplication.run(DannyApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        System.out.println("=========================================");
        System.out.println("  PROBANDO PL/SQL DESDE SPRING BOOT     ");
        System.out.println("=========================================");

        try {
            // Ejemplo de llamada a la función
            String nombre = plsqlService.getNombreCompleto(1);
            System.out.println("[SPRING] Nombre completo devuelto: " + nombre);

        } catch (Exception e) {
            System.err.println("[SPRING ERROR] " + e.getMessage());
        }
    }
}