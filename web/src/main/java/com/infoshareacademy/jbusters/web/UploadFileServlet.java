package com.infoshareacademy.jbusters.web;

import com.infoshareacademy.jbusters.data.DataLoader;
import com.infoshareacademy.jbusters.data.Transaction;
import com.infoshareacademy.jbusters.data.UploadFileFromUser;
import com.infoshareacademy.jbusters.freemarker.TemplateProvider;
import com.infoshareacademy.jbusters.model.User;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@WebServlet(urlPatterns = "/upload-file")
@MultipartConfig(location = "/tmp"
        , fileSizeThreshold = 1024 * 1024
        , maxFileSize = 1024 * 1024 * 5
        , maxRequestSize = 1024 * 1024 * 5 * 5)
public class UploadFileServlet extends HttpServlet {

    private static final Logger LOG = LoggerFactory.getLogger(UploadFileServlet.class);
    private static final String TEMPLATE_USERS_UPLOAD_FILE = "user-upload-file";
    private static final String TEMPLATE_UPLOAD_FILE = "upload-file";


    @Inject
    private TemplateProvider templateProvider;
    @Inject
    private UploadFileFromUser uploadFileFromUser;
    @Inject
    private DataLoader dataLoader;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        resp.addHeader("Content-Type", "text/html; charset=utf-8");
        PrintWriter out = resp.getWriter();

        Map<String, Object> model = new HashMap<>();

        HttpSession session = req.getSession(true);
        User sessionUser = (User) session.getAttribute("user");

        Template template;


        if (sessionUser == null){
            template = templateProvider.getTemplate(getServletContext(), TEMPLATE_UPLOAD_FILE);
            try {
                template.process(model, out);
                LOG.info("Loaded file");
            } catch (TemplateException e) {
                LOG.error("Failed to load file");
            }

        }else {
            template = templateProvider.getTemplate(getServletContext(), TEMPLATE_USERS_UPLOAD_FILE);
            model.put("user", sessionUser);
            try {
                template.process(model, out);
                LOG.info("Loaded file");
            } catch (TemplateException e) {
                LOG.error("Failed to load file");
            }
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html;charset=UTF-8");

        HttpSession session = req.getSession();
        User sessionUser = (User) session.getAttribute("user");

        final PrintWriter writer = resp.getWriter();
        final Part filePart = req.getPart("file");
        List<Transaction> usersTransactions = new ArrayList<>();
        Map<String, Object> model = new HashMap<>();
        model.put("user", sessionUser);

        Template template;
        if (sessionUser == null){
            template = templateProvider.getTemplate(getServletContext(), TEMPLATE_UPLOAD_FILE);
        }else {
            template = templateProvider.getTemplate(getServletContext(), TEMPLATE_USERS_UPLOAD_FILE);
        }

        String fileName;
        LOG.info("DEBUG zaczynam zapis");
        try {
            LOG.info("DEBUG zapis:");
            fileName = uploadFileFromUser.uploadFile(filePart).getName();
            LOG.info("DEBUG filename : " + fileName);
            Path path2 = Paths.get(System.getProperty("jboss.home.dir") + "/upload/" + fileName);
            LOG.info("DEBUG path: " + System.getProperty("jboss.home.dir") + "/upload/" + fileName);
            usersTransactions = createTransactionListFromFile(usersTransactions, fileName, path2);

        } catch (Exception e) {
            String errorMasage = "Błąd ładowania pliku. "
                    + "Możliwe że nie wskazałeś żadnego pliku do załadowania. ";
            model.put("error", errorMasage);
            LOG.error("Error with loading file. {}", e.getMessage());
        } finally {
            if (model.get("error") == null) {
                session.setAttribute("propertyList", usersTransactions);
                resp.sendRedirect("my-flats");
            } else {
                try {
                    template.process(model, writer);
                    session.setAttribute("propertyList", usersTransactions);
                    LOG.info("Loaded users flats. Number of flats: {}", usersTransactions.size());
                } catch (TemplateException e) {
                    LOG.error("Failed to load users flats. Number of flats: {}", usersTransactions.size());
                }
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



