package com.concentrados.Danny.service;

import com.concentrados.Danny.service.ReporteService;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Map;
import java.util.Collection;
import java.text.SimpleDateFormat;
import java.util.Date;
import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

@Service
public class ReporteServiceImpl implements ReporteService {

    @Override
    public void generarPdf(String reporte, Map<String, Object> parametros, HttpServletResponse response) throws IOException {
        try {
            System.out.println("--- INICIANDO EXPORTACIÓN DE PROFORMA: " + reporte + " ---");

            response.reset();

            String fecha = new SimpleDateFormat("dd-MM-yyyy").format(new Date());
            String nombreArchivo = "Proforma_Danny_" + fecha + ".pdf";

            response.setContentType("application/pdf");
            response.setHeader("Content-Disposition", "attachment; filename=\"" + nombreArchivo + "\"");

            String ruta = "reportes/" + reporte + ".jrxml";
            ClassPathResource resource = new ClassPathResource(ruta);
            
            if (!resource.exists()) {
                throw new IOException("El archivo de diseño no existe en: " + ruta);
            }

            try (InputStream inputStream = resource.getInputStream()) {
                JasperReport jasperReport = JasperCompileManager.compileReport(inputStream);
                System.out.println("Diseño JRXML compilado exitosamente.");

                Object items = parametros.get("items");
                JasperPrint jasperPrint;
                
                if (items instanceof Collection && !((Collection<?>) items).isEmpty()) {
                    JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource((Collection<?>) items, false);
                    jasperPrint = JasperFillManager.fillReport(jasperReport, parametros, dataSource);
                } else {
                    jasperPrint = JasperFillManager.fillReport(jasperReport, parametros, new JREmptyDataSource());
                }

                try (OutputStream out = response.getOutputStream()) {
                    JasperExportManager.exportReportToPdfStream(jasperPrint, out);
                    out.flush();
                }
            }

            System.out.println("--- PDF ENVIADO AL NAVEGADOR EXITOSAMENTE ---");
            
        } catch (JRException e) {
            System.err.println("Error técnico en JasperReports: " + e.getMessage());
            throw new IOException("No se pudo procesar el motor de reportes", e);
        } catch (Exception e) {
            System.err.println("Error inesperado en ReporteServiceImpl: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
