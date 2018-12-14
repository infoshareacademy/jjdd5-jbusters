package com.infoshareacademy.jbusters.data;

import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.kernel.colors.DeviceRgb;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.geom.Rectangle;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.kernel.pdf.canvas.PdfCanvas;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.element.Text;
import com.itextpdf.layout.property.TextAlignment;
import com.itextpdf.layout.property.UnitValue;

import java.io.*;
import java.net.URL;
import java.util.List;
import java.util.StringTokenizer;

import static com.itextpdf.io.font.PdfEncodings.CP1250;
import static com.itextpdf.io.font.constants.StandardFonts.HELVETICA;

public class ReportGenerator {

    private static final String TEMPLATE_NAME = "load-other-values";

    public static final String RAPORT_PATH = System.getProperty("jboss.server.temp.dir") + "/raport.pdf";
    public static final String FLAT_PATH = System.getProperty("jboss.home.dir") + "/upload/flats.txt";
    public static final URL BG_IMG_PATH = Thread.currentThread().getContextClassLoader().getResource("/img/JBusters_logo.png");
    private StatisticsManager statisticsManager= new StatisticsManager();
    private List<Statistics> statisticList;
    public ReportGenerator() throws FileNotFoundException {
    }

    public void generateReport() throws IOException {

        // Initialize PDF document
        PdfDocument pdf = new PdfDocument(new PdfWriter(RAPORT_PATH));

       // Initialize document
        Document doc = new Document(pdf);

        // Add content
        PdfFont polishFont = null;

        try {
            polishFont = PdfFontFactory.createFont(HELVETICA, CP1250, true, false);
        } catch (IOException e) {
            e.printStackTrace();
        }

        PdfCanvas canvas = new PdfCanvas(pdf.addNewPage());
        Rectangle rect = new Rectangle(doc.getPageEffectiveArea(PageSize.A4).getWidth() / 2 - 110, doc.getPageEffectiveArea(PageSize.A4).getHeight() / 2 - 100, 300, 300);
        canvas.addImage(ImageDataFactory.create(BG_IMG_PATH), rect, false);

        Text title = new Text("Raport statystyk użycia aplikacji").setFont(polishFont);
        Text author = new Text("JBusters").setFont(polishFont);
        Paragraph parag = new Paragraph().add(title).add(" by ").add("\n").add(author);
        parag.setTextAlignment(TextAlignment.CENTER).setFontSize(14f);
        parag.setMarginBottom(70f);
        doc.add(parag);
        canvas.setStrokeColor(new DeviceRgb(0, 0, 0)).moveTo(20, 750).lineTo(570, 750).closePathStroke();

        Table table = new Table(4);
        table.setWidth(UnitValue.createPercentValue(80));
        BufferedReader br = new BufferedReader(new FileReader(FLAT_PATH));
        String line;
        process(table, "Miasto,Dzielnica,Srednia wartosc wyceny, ilosc wycen", polishFont, true);
        while ((line = br.readLine()) != null) {
            process(table, line, polishFont, false);
        }
        br.close();
        doc.add(table);
        doc.flush();
        //Close document
        doc.close();


    }


    public void process(Table table, String line, PdfFont font, boolean isHeader) {
        StringTokenizer tokenizer = new StringTokenizer(line, ",");

        while (tokenizer.hasMoreTokens()) {
            if (isHeader) {
                table.addCell(
                        new Cell().add(
                                new Paragraph(tokenizer.nextToken()).setFont(font).setBold()));
            } else
                table.addCell(
                        new Cell().add(
                                new Paragraph(tokenizer.nextToken()).setFont(font)));

        }
    }

}
