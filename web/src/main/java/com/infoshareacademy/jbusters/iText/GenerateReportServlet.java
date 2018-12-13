package com.infoshareacademy.jbusters.iText;

        import com.itextpdf.io.font.FontConstants;
        import com.itextpdf.io.image.ImageDataFactory;
        import com.itextpdf.kernel.font.PdfFont;
        import com.itextpdf.kernel.font.PdfFontFactory;
        import com.itextpdf.kernel.geom.PageSize;
        import com.itextpdf.kernel.geom.Rectangle;
        import com.itextpdf.kernel.pdf.PdfDocument;
        import com.itextpdf.kernel.pdf.PdfWriter;
        import com.itextpdf.kernel.pdf.canvas.PdfCanvas;
        import com.itextpdf.layout.Canvas;
        import com.itextpdf.layout.Document;
        import com.itextpdf.layout.element.*;
        import com.itextpdf.layout.property.AreaBreakType;
        import com.itextpdf.layout.property.TextAlignment;
        import com.itextpdf.layout.property.UnitValue;
        import org.slf4j.Logger;
        import org.slf4j.LoggerFactory;

        import javax.servlet.ServletException;
        import javax.servlet.annotation.WebServlet;
        import javax.servlet.http.HttpServlet;
        import javax.servlet.http.HttpServletRequest;
        import javax.servlet.http.HttpServletResponse;
        import java.io.*;
        import java.net.URL;
        import java.util.StringTokenizer;


@WebServlet(urlPatterns = ("/generate-report"))
public class GenerateReportServlet extends HttpServlet {

    private static final String TEMPLATE_NAME = "load-other-values";
    private static final Logger LOG = LoggerFactory.getLogger(com.infoshareacademy.jbusters.web.LoadOtherValuesTransactionServlet.class);

    public static final String RAPORT_PATH = System.getProperty("jboss.home.dir") + "/upload/raport.pdf";
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
        PdfFont font = PdfFontFactory.createFont(FontConstants.TIMES_ROMAN);
        PdfFont bold = PdfFontFactory.createFont(FontConstants.TIMES_BOLD);

        PdfCanvas canvas = new PdfCanvas(pdf.addNewPage());
        Rectangle rect = new Rectangle(doc.getPageEffectiveArea(PageSize.A4).getWidth()/2-110, doc.getPageEffectiveArea(PageSize.A4).getHeight()/2-100, 300, 300);
        canvas.addImage(ImageDataFactory.create(BG_IMG_PATH),rect, false);

        Text title = new Text("Raport statystyk użycia aplikacji").setFont(bold);
        Text author = new Text("JBusters").setFont(font);
        Paragraph parag = new Paragraph().add(title).add(" by ").add("\n").add(author);
        parag.setTextAlignment(TextAlignment.CENTER);




        Table table = new Table(new float[]{8,4,4,5,6,1,3,1,1,7,8,3,1,9});
        table.setWidth(UnitValue.createPercentValue(100));
         BufferedReader br = new BufferedReader(new FileReader(FLAT_PATH));
        String line = br.readLine();
         process(table, line, bold, false);
         while ((line = br.readLine()) != null) {
             process(table, line, font, false);
             }
        br.close();


        doc.add(parag);
        doc.add(table);
        doc.flush();
        //Close document
        doc.close();



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