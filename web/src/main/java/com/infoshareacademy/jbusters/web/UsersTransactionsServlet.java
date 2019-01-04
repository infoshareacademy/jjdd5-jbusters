package com.infoshareacademy.jbusters.web;

import com.infoshareacademy.jbusters.data.DataLoader;
import com.infoshareacademy.jbusters.data.Transaction;
import com.infoshareacademy.jbusters.data.UploadFileFromUser;
import com.infoshareacademy.jbusters.freemarker.TemplateProvider;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
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
    private static final String TEMPLATE_NAME = "transactions-user";

    @Inject
    private TemplateProvider templateProvider;

    @Inject
    private UploadFileFromUser uploadFileFromUser;

    @Inject
    private DataLoader dataLoader;

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");

        HttpSession session = request.getSession();
        final PrintWriter writer = response.getWriter();
        final Part filePart = request.getPart("file");
        List<Transaction> usersTransactions = new ArrayList<>();
        Map<String, Object> model = new HashMap<>();

        Template template = templateProvider.getTemplate(
                getServletContext(),
                TEMPLATE_NAME);

        // TODO błąd gdy wybierze się plik do pobrania a następnie go przeniesie/usunie z dysku i kliknie się na pobierz

        String fileName;
        try {
            fileName = uploadFileFromUser.uploadFile(filePart).getName();
            Path path2 = Paths.get(System.getProperty("jboss.home.dir") + "/upload/" + fileName);
            usersTransactions = createTransactionListFromFile(usersTransactions, fileName, path2);
            model.put("flats", usersTransactions);
        } catch (Exception e) {
            String errorMasage = "Błąd ładowania pliku. "
                    + "Możliwe że nie wskazałeś żadnego pliku do załadowania. ";
            model.put("error", errorMasage);
            LOG.error("Error with loading file. {}", e.getMessage());
        } finally {
            try {
                template.process(model, writer);
                session.setAttribute("propertyList", usersTransactions);
                LOG.info("Loaded users flats. Number of flats: {}", usersTransactions.size());
            } catch (TemplateException e) {
                LOG.error("Failed to load users flats. Number of flats: {}", usersTransactions.size());
            }
            if (writer != null) {
                writer.close();
            }
        }
    }

    private List<Transaction> createTransactionListFromFile(List<Transaction> usersTransactions, String fileName, Path path2) {
        try {
            usersTransactions = dataLoader.createTransactionList(Files.readAllLines(path2), true);
            LOG.info("Loading file with name {}", fileName);
        } catch (Exception e) {
            LOG.error("File loading error {}", e.getMessage());
        }
        return usersTransactions;
    }
}
