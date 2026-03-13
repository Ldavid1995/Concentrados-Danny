package com.concentrados.Danny.service;

import com.lowagie.text.*;
import com.lowagie.text.pdf.*;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import com.concentrados.Danny.domain.Producto;

public class ProductoPdfService {
    public void exportar(HttpServletResponse response, List<Producto> productos) throws IOException {
        Document documento = new Document(PageSize.A4);
        PdfWriter.getInstance(documento, response.getOutputStream());
        
        documento.open();
        
        // Título
        Font fuenteTitulo = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 18);
        Paragraph titulo = new Paragraph("Catálogo de Precios - Concentrados Danny", fuenteTitulo);
        titulo.setAlignment(Paragraph.ALIGN_CENTER);
        documento.add(titulo);
        
        documento.add(new Paragraph(" ")); // Espacio
        
        // Tabla
        PdfPTable tabla = new PdfPTable(4);
        tabla.setWidthPercentage(100);
        
        // Cabeceras
        tabla.addCell("Producto");
        tabla.addCell("Marca");
        tabla.addCell("Unidad");
        tabla.addCell("Precio");
        
        for (Producto p : productos) {
            tabla.addCell(p.getNombre());
            tabla.addCell(p.getMarca());
            tabla.addCell(p.getUnidadMedida());
            tabla.addCell("₡" + p.getPrecio());
        }
        
        documento.add(tabla);
        documento.close();
    }
}

