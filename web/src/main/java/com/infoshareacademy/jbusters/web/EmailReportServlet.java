package com.infoshareacademy.jbusters.web;

import com.infoshareacademy.jbusters.data.MailHandler;
import com.infoshareacademy.jbusters.data.ReportGenerator;
import com.infoshareacademy.jbusters.data.StaticFields;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.mail.MessagingException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

@WebServlet("/generate-email-report")
public class EmailReportServlet extends HttpServlet {

    private static final Logger LOGGER = LoggerFactory.getLogger(EmailReportServlet.class);
    private static final String REPORT_PATH = StaticFields.getReportPathString();

    @Inject
    private ReportGenerator reportGenerator;

    @Inject
    private MailHandler mailHandler;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        reportGenerator.generateReport();
        LOGGER.info("Report generated under following path: {}", REPORT_PATH);

        try {
            mailHandler.executor();
        } catch (MessagingException e) {
            e.printStackTrace();
        }

        Files.deleteIfExists(Paths.get(REPORT_PATH));
        LOGGER.info("Report deleted from following path: {}", REPORT_PATH);
    }
}
