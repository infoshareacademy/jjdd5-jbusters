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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.enterprise.context.RequestScoped;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static com.itextpdf.io.font.PdfEncodings.CP1250;
import static com.itextpdf.io.font.constants.StandardFonts.HELVETICA;

@RequestScoped
public class ReportGenerator {


    private static final Logger LOGGER = LoggerFactory.getLogger(Data.class);
    private StatisticsManager statisticsManager;
    private PropLoader properties;
    private static final String CITY_TABLE_HEADER = "MIASTO|ILOŚĆ WYSZUKIWAŃ|SUMARYCZNA WARTOŚĆ WYCEN";
    private static final String DISTRICT_TABLE_HEADER = "DZIELNICA|ILOŚĆ WYSZUKIWAŃ|SUMARYCZNA WARTOŚĆ WYCEN|ŚREDNIA WARTOŚĆ WYCEN";


    public ReportGenerator() {
        this.statisticsManager = new StatisticsManager();
        properties = new PropLoader();
        try {
            properties = new PropLoader(StaticFields.getAppPropertiesURL().openStream());
            String currency = properties.getCurrency();
        } catch (Exception e) {
            LOGGER.error("Missing properties file in path {}", StaticFields.getAppPropertiesURL().toString());
        }

    }

    public void generateReport() throws IOException {

        PdfDocument pdf = new PdfDocument(new PdfWriter(StaticFields.getReportPathString()));
        Document doc = new Document(pdf);

        PdfFont polishFont = PdfFontFactory.createFont(HELVETICA, CP1250, true, true);
        PdfCanvas canvas = new PdfCanvas(pdf.addNewPage());
        Rectangle rect = new Rectangle(doc.getPageEffectiveArea(PageSize.A4).getWidth() / 2 - 110, doc.getPageEffectiveArea(PageSize.A4).getHeight() / 2 - 100, 300, 300);
        canvas.addImage(ImageDataFactory.create(StaticFields.getBgImgPath()), rect, false);

        Text title = new Text("Raport statystyk użycia aplikacji").setFont(polishFont).setFontSize(20f);
        Text author = new Text("JBusters").setFont(polishFont);
        Paragraph parag = new Paragraph().add(title).add(" \nwykonany przez team").add(": ").add(author);
        parag.setTextAlignment(TextAlignment.CENTER).setFontSize(16f);
        parag.setMarginBottom(80f);
        doc.add(parag);

        canvas.setStrokeColor(new DeviceRgb(0, 0, 0)).moveTo(20, 730).lineTo(580, 730).closePathStroke();


        ArrayList<Statistics> wholeStatisticList = new ArrayList<>(statisticsManager.generateStatisticsList());
        ArrayList<String> cityStatisticList = new ArrayList<>(statisticsManager.getCitesStatistics(wholeStatisticList));
        ArrayList<String> districtStatisticList = new ArrayList<>(statisticsManager.getDistrictsStatistics(wholeStatisticList));

        Paragraph parag1 = new Paragraph().add("Tabela statystyk dla miast (porządek alfabetyczny):");
        parag1.setFont(polishFont);
        parag1.setTextAlignment(TextAlignment.CENTER).setFontSize(16f);
        parag1.setMarginBottom(20f);

        doc.add(parag1);
        doc.add(tableCreator(CITY_TABLE_HEADER, cityStatisticList, polishFont, 100));

        Paragraph parag2 = new Paragraph().add("Tabela statystyk dla dzielnic (porządek alfabetyczny):");
        parag2.setFont(polishFont);
        parag2.setTextAlignment(TextAlignment.CENTER).setFontSize(16f);
        parag2.setMarginBottom(20f);
        doc.add(parag2);

        doc.add(tableCreator(DISTRICT_TABLE_HEADER, districtStatisticList, polishFont, 100));

        doc.flush();
        doc.close();
    }

    private Table tableCreator(String headerCommaSeparated, List<String> content, PdfFont font, float widthPercentValue) {
        int colCounter = headerCommaSeparated.split("\\|").length;
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
        String[] inputLineTable = line.split("\\|");

        for(String s : inputLineTable){
            Cell cell = new Cell();
            cell.setVerticalAlignment(VerticalAlignment.MIDDLE);
            cell.add(new Paragraph(s).setFont(font));
            if (isHeader) cell.setBold();
            table.addCell(cell);
        }

    }

}
