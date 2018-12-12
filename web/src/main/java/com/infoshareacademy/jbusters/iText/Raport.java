package com.infoshareacademy.jbusters.iText;

import com.itextpdf.io.font.FontConstants;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Text;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Raport {

    public static final String RAPORT_PATH = System.getProperty("jboss.home.dir") + "/upload/raport.pdf";

    public static void main(String args[]) throws IOException {
        File file = new File(RAPORT_PATH);
        file.getParentFile().mkdirs();
        new Raport().createPdf(RAPORT_PATH);
    }

    public void createPdf(String dest) throws IOException {
        // Initialize PDF document
        PdfDocument pdf = new PdfDocument(new PdfWriter(dest));

        // Initialize document
        Document document = new Document(pdf);

        // Add content
        PdfFont font = PdfFontFactory.createFont(FontConstants.TIMES_ROMAN);
        PdfFont bold = PdfFontFactory.createFont(FontConstants.TIMES_BOLD);
        Text title = new Text("Raport statystyk uzycia aplikacji").setFont(bold);
        Text author = new Text("JBusters").setFont(font);
        Paragraph p = new Paragraph().add(title).add(" by ").add(author);
        document.add(p);

        //Close document
        document.close();
    }
}
