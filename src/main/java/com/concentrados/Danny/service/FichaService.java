package com.concentrados.Danny.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.nio.file.*;

@Service
public class FichaService {
    
    private final String folder = "src/main/resources/static/fichas/";

    public String guardarFicha(MultipartFile archivo) {
        if (archivo.isEmpty()) return null;
        
        try {
            Path path = Paths.get(folder);
            if (!Files.exists(path)) Files.createDirectories(path);
            
            // LIMPIEZA DEL NOMBRE (ID-12 Mejorada)
            // Quitamos espacios, tildes y caracteres raros
            String nombreOriginal = archivo.getOriginalFilename();
            String nombreLimpio = nombreOriginal.replaceAll("\\s+", "_") // Espacios a guiones bajos
                                               .replaceAll("[^a-zA-Z0-9._-]", ""); // Solo letras, números y puntos
            
            String nombreFinal = System.currentTimeMillis() + "_" + nombreLimpio;
            Path rutaCompleta = path.resolve(nombreFinal);
            
            Files.copy(archivo.getInputStream(), rutaCompleta, StandardCopyOption.REPLACE_EXISTING);
            
            return nombreFinal; 
        } catch (IOException e) {
            return null;
        }
    }
}