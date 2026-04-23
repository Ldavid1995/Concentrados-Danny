package com.concentrados.Danny.service;

import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

public interface ReporteService {
    // Método necesario para generar el PDF de la HU-24 y otros reportes
    public void generarPdf(String reporte, Map<String, Object> parametros, HttpServletResponse response) throws IOException;
}