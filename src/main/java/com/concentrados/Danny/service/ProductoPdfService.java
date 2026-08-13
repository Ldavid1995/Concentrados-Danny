package com.concentrados.Danny.service;

import com.concentrados.Danny.domain.Producto;
import com.lowagie.text.*;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import org.springframework.stereotype.Service;

import java.awt.Color;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.List;

@Service
public class ProductoPdfService {

    public ByteArrayInputStream generarReporteProductos(List<Producto> productos) {
        Document document = new Document(PageSize.A4);
        ByteArrayOutputStream out = new ByteArrayOutputStream();

        try {
            PdfWriter.getInstance(document, out);
            document.open();

            // Título del documento
            Font fontTitulo = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 18, Color.DARK_GRAY);
            Paragraph titulo = new Paragraph("Reporte de Inventario - Concentrados Danny", fontTitulo);
            titulo.setAlignment(Element.ALIGN_CENTER);
            titulo.setSpacingAfter(20);
            document.add(titulo);

            // Tabla de productos (5 columnas)
            PdfPTable table = new PdfPTable(5);
            table.setWidthPercentage(100);
            table.setWidths(new float[]{1, 3, 2, 2, 2});

            // Encabezados
            Font fontHeader = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 10, Color.WHITE);
            String[] headers = {"ID", "Producto", "Marca", "Precio", "Stock"};

            for (String header : headers) {
                PdfPCell cell = new PdfPCell(new Phrase(header, fontHeader));
                cell.setBackgroundColor(Color.GRAY);
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                cell.setPadding(6);
                table.addCell(cell);
            }

            // Filas de datos
            Font fontBody = FontFactory.getFont(FontFactory.HELVETICA, 9, Color.BLACK);
            for (Producto p : productos) {
                table.addCell(new PdfPCell(new Phrase(String.valueOf(p.getIdProducto()), fontBody)));
                table.addCell(new PdfPCell(new Phrase(p.getNombre() != null ? p.getNombre() : "", fontBody)));
                table.addCell(new PdfPCell(new Phrase(p.getMarca() != null ? p.getMarca() : "", fontBody)));
                table.addCell(new PdfPCell(new Phrase(p.getPrecio() != null ? "₡" + p.getPrecio().toString() : "₡0.00", fontBody)));
                table.addCell(new PdfPCell(new Phrase(String.valueOf(p.getStock()), fontBody)));
            }

            document.add(table);
            document.close();

        } catch (DocumentException ex) {
            ex.printStackTrace();
        }

        return new ByteArrayInputStream(out.toByteArray());
    }
}