package com.infoshareacademy.jbusters.web;

import com.infoshareacademy.jbusters.data.MailHandler;
import com.infoshareacademy.jbusters.data.ReportGenerator;
import com.infoshareacademy.jbusters.data.StaticFields;
import org.apache.commons.io.IOUtils;

import javax.inject.Inject;
import javax.mail.MessagingException;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;

@WebServlet("/generate-email-report")
public class EmailReportServlet extends HttpServlet {

    @Inject
    private ReportGenerator reportGenerator;

    @Inject
    private MailHandler mailHandler;

    public EmailReportServlet() {
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        //reportGenerator.generateReport();

        try {
            mailHandler.executor();
        } catch (MessagingException e) {
            e.printStackTrace();
        }

        //Files.deleteIfExists(Paths.get(REPORT_PATH));
    }
}
