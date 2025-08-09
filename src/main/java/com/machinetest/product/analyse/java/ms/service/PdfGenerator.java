package com.machinetest.product.analyse.java.ms.service;

import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.machinetest.product.analyse.java.ms.entity.ProductEntity;
import org.springframework.stereotype.Component;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.List;

@Component
public class PdfGenerator {

    public ByteArrayInputStream generateProductPdf(List<ProductEntity> products) {
        try (ByteArrayOutputStream out = new ByteArrayOutputStream()) {
            PdfWriter writer = new PdfWriter(out);
            PdfDocument pdfDoc = new PdfDocument(writer);
            Document doc = new Document(pdfDoc);

            doc.add(new Paragraph("Product Report").setBold().setFontSize(18));

            float[] colWidths = {50F, 100F, 200F, 80F, 80F, 100F};
            Table table = new Table(colWidths);

            table.addHeaderCell("ID");
            table.addHeaderCell("Name");
            table.addHeaderCell("Description");
            table.addHeaderCell("Price");
            table.addHeaderCell("Quantity");
            table.addHeaderCell("Revenue");

            for (ProductEntity p : products) {
                table.addCell(String.valueOf(p.getId()));
                table.addCell(p.getName());
                table.addCell(p.getDescription());
                table.addCell(String.valueOf(p.getPrice()));
                table.addCell(String.valueOf(p.getQuantity()));
                table.addCell(String.valueOf(p.getRevenue()));
            }

            doc.add(table);
            doc.close();

            return new ByteArrayInputStream(out.toByteArray());
        } catch (Exception e) {
            throw new RuntimeException("Error generating PDF", e);
        }
    }
}