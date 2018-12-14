package com.infoshareacademy.jbusters.iText;

        import com.itextpdf.io.font.FontConstants;
        import com.itextpdf.io.font.FontProgramFactory;
        import com.itextpdf.io.image.ImageDataFactory;
        import com.itextpdf.kernel.colors.Color;
        import com.itextpdf.kernel.colors.DeviceRgb;
        import com.itextpdf.kernel.font.PdfFont;
        import com.itextpdf.kernel.font.PdfFontFactory;
        import com.itextpdf.kernel.geom.Line;
        import com.itextpdf.kernel.geom.PageSize;
        import com.itextpdf.kernel.geom.Rectangle;
        import com.itextpdf.kernel.pdf.PdfDocument;
        import com.itextpdf.kernel.pdf.PdfWriter;
        import com.itextpdf.kernel.pdf.canvas.PdfCanvas;
        import com.itextpdf.kernel.pdf.canvas.draw.DottedLine;
        import com.itextpdf.kernel.pdf.canvas.draw.ILineDrawer;
        import com.itextpdf.kernel.pdf.colorspace.PdfDeviceCs;
        import com.itextpdf.layout.Canvas;
        import com.itextpdf.layout.Document;
        import com.itextpdf.layout.element.*;
        import com.itextpdf.layout.property.AreaBreakType;
        import com.itextpdf.layout.property.TextAlignment;
        import com.itextpdf.layout.property.UnitValue;
        import com.sun.javafx.font.FontFactory;
        import org.slf4j.Logger;
        import org.slf4j.LoggerFactory;

        import javax.servlet.ServletContext;
        import javax.servlet.ServletException;
        import javax.servlet.annotation.WebServlet;
        import javax.servlet.http.HttpServlet;
        import javax.servlet.http.HttpServletRequest;
        import javax.servlet.http.HttpServletResponse;
        import javax.swing.*;
        import java.io.*;
        import java.net.URL;
        import java.nio.file.Files;
        import java.nio.file.Paths;
        import java.util.StringTokenizer;

        import static com.itextpdf.io.font.PdfEncodings.CP1250;
        import static com.itextpdf.io.font.constants.StandardFonts.HELVETICA;


@WebServlet(urlPatterns = ("/generate-report"))
public class GenerateReportServlet extends HttpServlet {

    private static final String TEMPLATE_NAME = "load-other-values";
    private static final Logger LOG = LoggerFactory.getLogger(com.infoshareacademy.jbusters.web.LoadOtherValuesTransactionServlet.class);

    public static final String RAPORT_PATH = System.getProperty("jboss.server.temp.dir") + "/raport.pdf";
    public static final String FLAT_PATH = System.getProperty("jboss.home.dir") + "/upload/flats.txt";
    public static final URL BG_IMG_PATH = Thread.currentThread().getContextClassLoader().getResource("/img/JBusters_logo.png");

    OutputStream fos = new FileOutputStream(RAPORT_PATH);
    PdfWriter writer = new PdfWriter(fos);
    PdfDocument pdf = new PdfDocument(writer);
    Document document = new Document(pdf);
    Paragraph paragraph = new Paragraph("Hello JBusters");


    public GenerateReportServlet() throws FileNotFoundException {
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        // Initialize PDF document
        PdfDocument pdf = new PdfDocument(new PdfWriter(RAPORT_PATH));

        // Initialize document
        Document doc = new Document(pdf);

        // Add content
        PdfFont polishFont = PdfFontFactory.createFont(HELVETICA,CP1250,true,false);
        PdfFont font = PdfFontFactory.createFont(FontConstants.HELVETICA);
        PdfFont bold = PdfFontFactory.createFont(FontConstants.HELVETICA_BOLD);

        PdfCanvas canvas = new PdfCanvas(pdf.addNewPage());
        Rectangle rect = new Rectangle(doc.getPageEffectiveArea(PageSize.A4).getWidth()/2-110, doc.getPageEffectiveArea(PageSize.A4).getHeight()/2-100, 300, 300);
        canvas.addImage(ImageDataFactory.create(BG_IMG_PATH),rect, false);

        Text title = new Text("Raport statystyk użycia aplikacji").setFont(polishFont);
        Text author = new Text("JBusters").setFont(font);
        Paragraph parag = new Paragraph().add(title).add(" by ").add("\n").add(author);
        parag.setTextAlignment(TextAlignment.CENTER);

        doc.add(parag);
        canvas.setStrokeColor(new DeviceRgb(0,0,0)).moveTo(20,750).lineTo(500,750).closePathStroke();

        Table table = new Table(UnitValue.createPercentArray(20));
        table.setWidth(UnitValue.createPercentValue(80));
         BufferedReader br = new BufferedReader(new FileReader(FLAT_PATH));
        String line = br.readLine();
         process(table, line, bold, false);
         while ((line = br.readLine()) != null) {
             process(table, line, font, false);
             }
        br.close();

        doc.flush();
        //Close document
        doc.close();

        resp.setHeader("Content-Disposition", "attachment; filename=\"Raport.pdf\"");
        // reads input file from an absolute path
        File downloadFile = new File(RAPORT_PATH);
        FileInputStream inStream = new FileInputStream(downloadFile);


        // obtains ServletContext
        ServletContext context = getServletContext();

        // gets MIME type of the file
        String mimeType = context.getMimeType(RAPORT_PATH);
        if (mimeType == null) {
            // set to binary type if MIME mapping not found
            mimeType = "application/octet-stream";
        }

        // modifies response
        resp.setContentType(mimeType);
        resp.setContentLength((int) downloadFile.length());

        // forces download
        String headerKey = "Content-Disposition";
        String headerValue = String.format("attachment; filename=\"%s\"", downloadFile.getName());
        resp.setHeader(headerKey, headerValue);

        // obtains response's output stream
        OutputStream outStream = resp.getOutputStream();

        byte[] buffer = new byte[4096];
        int bytesRead = -1;

        while ((bytesRead = inStream.read(buffer)) != -1) {
            outStream.write(buffer, 0, bytesRead);
        }

        inStream.close();
        outStream.close();

        Files.deleteIfExists(Paths.get(RAPORT_PATH));

    }
    public void process(Table table, String line, PdfFont font, boolean isHeader) {
        StringTokenizer tokenizer = new StringTokenizer(line, ",");
        while (tokenizer.hasMoreTokens()) {
            if (isHeader) {
                table.addHeaderCell(
                        new Cell().add(
                                new Paragraph(tokenizer.nextToken()).setFont(font)));
            } else {
                table.addCell(
                        new Cell().add(
                                new Paragraph(tokenizer.nextToken()).setFont(font)));
            }
        }
    }






}