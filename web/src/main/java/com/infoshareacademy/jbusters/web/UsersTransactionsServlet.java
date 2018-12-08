package com.infoshareacademy.jbusters.web;

import com.infoshareacademy.jbusters.data.DataLoader;
import com.infoshareacademy.jbusters.data.FilterTransactions;
import com.infoshareacademy.jbusters.data.Transaction;
import com.infoshareacademy.jbusters.freemarker.TemplateProvider;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@WebServlet(urlPatterns = "/users-transactions")
@MultipartConfig(location = "/tmp"
        , fileSizeThreshold = 1024 * 1024
        , maxFileSize = 1024 * 1024 * 5
        , maxRequestSize = 1024 * 1024 * 5 * 5)
public class UsersTransactionsServlet extends HttpServlet {

    private static final Logger LOG = LoggerFactory.getLogger(UsersTransactionsServlet.class);
    private static final String TEMPLATE_NAME = "users-transactions";

    @Inject
    private TemplateProvider templateProvider;

    @Inject
    private FilterTransactions filterTransactions;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.addHeader("Content-Type", "text/html; charset=utf-8");

        DataLoader dataLoader = new DataLoader();

        Path path = Paths.get(System.getProperty("jboss.home.dir") + "/upload/test.txt");

        List<Transaction> usersTransactions = dataLoader.createTransactionList(Files.readAllLines(path), "yes");

        PrintWriter out = resp.getWriter();


        Template template = templateProvider.getTemplate(
                getServletContext(),
                TEMPLATE_NAME);


        Map<String, Object> model = new HashMap<>();
        model.put("flats", usersTransactions);

        try {
            template.process(model, out);
        } catch (TemplateException e) {
            e.printStackTrace();
        }

    }
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");

        // Create path components to save the file
        final String path = System.getProperty("jboss.home.dir") + "/upload";
        final Part filePart = request.getPart("file");
        final String fileName = getFileName(filePart);

        DataLoader dataLoader = new DataLoader();

        OutputStream out = null;
        InputStream filecontent = null;
        final PrintWriter writer = response.getWriter();

        try {
            out = new FileOutputStream(new File(path + File.separator
                    + fileName));
            filecontent = filePart.getInputStream();

            int read = 0;
            final byte[] bytes = new byte[1024];

            while ((read = filecontent.read(bytes)) != -1) {
                out.write(bytes, 0, read);
            }

            Path path2 = Paths.get(System.getProperty("jboss.home.dir") + "/upload/" + fileName);

            List<Transaction> usersTransactions = dataLoader.createTransactionList(Files.readAllLines(path2), "yes");

            Template template = templateProvider.getTemplate(
                    getServletContext(),
                    TEMPLATE_NAME);

            Map<String, Object> model = new HashMap<>();
            model.put("flats", usersTransactions);

            try {
                template.process(model, writer);
            } catch (TemplateException e) {
                e.printStackTrace();
            }

        } catch (FileNotFoundException fne) {
            writer.println("You either did not specify a file to upload or are "
                    + "trying to upload a file to a protected or nonexistent "
                    + "location.");
            writer.println("<br/> ERROR: " + fne.getMessage());
        } finally {
            if (out != null) {
                out.close();
            }
            if (filecontent != null) {
                filecontent.close();
            }
            if (writer != null) {
                writer.close();
            }
        }
    }
    private String getFileName(final Part part) {
        final String partHeader = part.getHeader("content-disposition");
        for (String content : part.getHeader("content-disposition").split(";")) {
            if (content.trim().startsWith("filename")) {
                return content.substring(
                        content.indexOf('=') + 1).trim().replace("\"", "");
            }
        }
        return null;
    }

}
