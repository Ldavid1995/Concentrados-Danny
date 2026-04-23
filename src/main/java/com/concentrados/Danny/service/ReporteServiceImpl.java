package com.concentrados.Danny.service;

import com.concentrados.Danny.service.ReporteService;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

@Service
public class ReporteServiceImpl implements ReporteService {

    @Override
    public void generarPdf(String reporte, Map<String, Object> parametros, HttpServletResponse response) throws IOException {
        try {
            System.out.println("--- INICIANDO GENERACIÓN DE PDF: " + reporte + " ---");

            // 1. Configurar el encabezado de la respuesta
            response.setContentType("application/pdf");
            response.addHeader("Content-disposition", "inline; filename=" + reporte + ".pdf");

            // 2. Cargar el archivo .jasper
            // IMPORTANTE: Verifica que el archivo esté en src/main/resources/reportes/cotizacion.jasper
            String ruta = "reportes/" + reporte + ".jasper";
            ClassPathResource resource = new ClassPathResource(ruta);
            
            System.out.println("Buscando archivo en: " + resource.getPath());

            if (!resource.exists()) {
                System.err.println("ERROR: El archivo " + ruta + " no existe físicamente.");
                throw new IOException("No se encontró el archivo .jasper en la ruta especificada.");
            }

            InputStream inputStream = resource.getInputStream();
            System.out.println("Archivo cargado correctamente.");

            // 3. Llenar el reporte
            // Si pasas una lista de items en el map, Jasper necesita un DataSource
            // Usamos JRBeanCollectionDataSource si 'items' es una lista, o JREmptyDataSource si no
            Object items = parametros.get("items");
            JasperPrint jasperPrint;
            
            if (items instanceof java.util.Collection) {
                System.out.println("Detectada colección de items, usando JRBeanCollectionDataSource.");
                jasperPrint = JasperFillManager.fillReport(inputStream, parametros, 
                        new JRBeanCollectionDataSource((java.util.Collection<?>) items));
            } else {
                System.out.println("No se detectó colección, usando DataSource vacío.");
                jasperPrint = JasperFillManager.fillReport(inputStream, parametros, new JREmptyDataSource());
            }

            // 4. Exportar al navegador
            JasperExportManager.exportReportToPdfStream(jasperPrint, response.getOutputStream());
            System.out.println("--- PDF ENVIADO AL NAVEGADOR EXITOSAMENTE ---");
            
        } catch (JRException e) {
            System.err.println("Error crítico en JasperReports: " + e.getMessage());
            e.printStackTrace();
        } catch (Exception e) {
            System.err.println("Error inesperado: " + e.getMessage());
            e.printStackTrace();
        }
    }
}