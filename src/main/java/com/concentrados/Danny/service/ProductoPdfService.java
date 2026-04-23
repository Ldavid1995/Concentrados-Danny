package com.concentrados.Danny.service;

import com.lowagie.text.*;
import com.lowagie.text.pdf.*;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import com.concentrados.Danny.domain.Producto;
import org.springframework.stereotype.Service;

@Service
public class ProductoPdfService {
    
    /**
     * Exporta el catálogo de productos a PDF.
     * Se agrega DocumentException a la firma para solucionar el error de compilación de Maven.
     */
    public void exportar(HttpServletResponse response, List<Producto> productos) throws DocumentException, IOException {
        Document documento = new Document(PageSize.A4);
        
        // El método getInstance lanza DocumentException, ahora sí está declarado en el 'throws'
        PdfWriter.getInstance(documento, response.getOutputStream());
        
        documento.open();
        
        // Título del Reporte - Concentrados Danny
        Font fuenteTitulo = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 18);
        Paragraph titulo = new Paragraph("Catálogo de Precios - Concentrados Danny", fuenteTitulo);
        titulo.setAlignment(Paragraph.ALIGN_CENTER);
        documento.add(titulo);
        
        documento.add(new Paragraph(" ")); // Espacio en blanco
        
        // Tabla de datos numéricos y detalles
        PdfPTable tabla = new PdfPTable(4);
        tabla.setWidthPercentage(100);
        
        // Estilo básico para cabeceras
        tabla.addCell("Producto");
        tabla.addCell("Marca");
        tabla.addCell("Unidad");
        tabla.addCell("Precio");
        
        // Llenado de datos desde la base de datos
        for (Producto p : productos) {
            tabla.addCell(p.getNombre() != null ? p.getNombre() : "N/A");
            tabla.addCell(p.getMarca() != null ? p.getMarca() : "N/A");
            tabla.addCell(p.getUnidadMedida() != null ? p.getUnidadMedida() : "Unidad");
            tabla.addCell("₡" + String.format("%.2f", p.getPrecio()));
        }
        
        documento.add(tabla);
        documento.close();
    }
}