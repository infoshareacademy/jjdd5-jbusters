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
import com.itextpdf.layout.property.VerticalAlignment;

import javax.enterprise.context.RequestScoped;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import static com.itextpdf.io.font.PdfEncodings.*;
import static com.itextpdf.io.font.constants.StandardFonts.HELVETICA;
import static com.itextpdf.io.font.constants.StandardFonts.TIMES_ROMAN;

@RequestScoped
public class ReportGenerator {


    public static final String RAPORT_PATH = System.getProperty("jboss.server.temp.dir") + "/raport.pdf";
    public static final URL BG_IMG_PATH = Thread.currentThread().getContextClassLoader().getResource("/img/JBusters_logo.png");
    private StatisticsManager statisticsManager = new StatisticsManager();
    private static final String CITY_TABLE_HEADER = "MIASTO,ILOŚĆ WYSZUKIWAŃ,CAŁKOWITA KWOTA WYCEN";
    private static final String DISTRICT_TABLE_HEADER = "DZIELNICA,ILOŚĆ WYSZUKIWAŃ,CAŁKOWITA KWOTA WYCEN,ŚREDNIA KWOTA WYCENY";


    public ReportGenerator() {
    }

    public void generateReport() throws IOException {

        PdfDocument pdf = new PdfDocument(new PdfWriter(RAPORT_PATH));
        Document doc = new Document(pdf);

        PdfFont polishFont = null;
        try {
            polishFont = PdfFontFactory.createFont(HELVETICA,CP1250, true, true);
        } catch (IOException e) {
            e.printStackTrace();
        }

        PdfCanvas canvas = new PdfCanvas(pdf.addNewPage());
        Rectangle rect = new Rectangle(doc.getPageEffectiveArea(PageSize.A4).getWidth() / 2 - 110, doc.getPageEffectiveArea(PageSize.A4).getHeight() / 2 - 100, 300, 300);
        canvas.addImage(ImageDataFactory.create(BG_IMG_PATH), rect, false);

        Text title = new Text("Raport statystyk użycia aplikacji").setFont(polishFont).setFontSize(20f);
        Text author = new Text("JBusters").setFont(polishFont);
        Paragraph parag = new Paragraph().add(title).add(" \nwykonany przez team").add(": ").add(author);
        parag.setTextAlignment(TextAlignment.CENTER).setFontSize(16f);
        parag.setMarginBottom(70f);
        doc.add(parag);


        canvas.setStrokeColor(new DeviceRgb(0, 0, 0)).moveTo(20, 730).lineTo(580, 730).closePathStroke();


        ArrayList<Statistics> wholeStatisticList = new ArrayList<Statistics>(statisticsManager.generateStatisticsList());
        ArrayList<String> cityStatisticList = new ArrayList<String>(statisticsManager.getCitesStatistics(wholeStatisticList));
        ArrayList<String> districtStatisticList = new ArrayList<String>(statisticsManager.getDistrictsStatistics(wholeStatisticList));

        doc.add(tableCreator(CITY_TABLE_HEADER, cityStatisticList, polishFont, 100));
        doc.add(tableCreator(DISTRICT_TABLE_HEADER, districtStatisticList, polishFont, 100));

        doc.flush();
        doc.close();
    }

    private Table tableCreator(String headerCommaSeparated, List<String> content, PdfFont font, float widthPercentValue) {
        int colCounter = headerCommaSeparated.split(",").length;
        Table table = new Table(colCounter);
        table.setWidth(UnitValue.createPercentValue(widthPercentValue));
        table.setTextAlignment(TextAlignment.CENTER);
        table.setMarginBottom(50f);

        addCells(table, headerCommaSeparated, font, true);
        for (String line : content) {
            addCells(table, line, font, false);
        }
        return table;
    }

    private void addCells(Table table, String line, PdfFont font, boolean isHeader) {
        StringTokenizer tokenizer = new StringTokenizer(line, ",");

        while (tokenizer.hasMoreTokens()) {
            Cell cell = new Cell();
            cell.setVerticalAlignment(VerticalAlignment.MIDDLE);
            cell.add(new Paragraph(tokenizer.nextToken()).setFont(font));
            if (isHeader) cell.setBold();

            table.addCell(cell);
        }
    }

}
