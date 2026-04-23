package com.concentrados.Danny.service;

import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

public interface ReporteService {
    public void generarPdf(String reporte, Map<String, Object> parametros, HttpServletResponse response) throws IOException;
}