package com.infoshareacademy.jbusters.web;

import com.infoshareacademy.jbusters.data.ReportGenerator;
import com.infoshareacademy.jbusters.data.StaticFields;
import com.infoshareacademy.jbusters.model.AppProperty;
import org.apache.commons.io.IOUtils;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
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
import java.util.List;

@WebServlet("/generate-report")
public class GenerateReportServlet extends HttpServlet {

    String REPORT_PATH;

    @Inject
    private ReportGenerator reportGenerator;

    @Inject
    StaticFields staticFields;

    @PostConstruct
    public void init() {
      REPORT_PATH = staticFields.getReportPathString();

    }


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        reportGenerator.generateReport();

        resp.setHeader("Content-Disposition", "attachment; filename=\"Raport.pdf\"");
        // reads input file from an absolute path
        File downloadFile = new File(REPORT_PATH);
        FileInputStream inStream = new FileInputStream(downloadFile);


        // obtains ServletContext
        ServletContext context = getServletContext();

        // gets MIME type of the file
        String mimeType = context.getMimeType(REPORT_PATH);
        if (mimeType == null) {
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

        IOUtils.copy(inStream, outStream);
        inStream.close();
        outStream.close();

        Files.deleteIfExists(Paths.get(REPORT_PATH));

    }


}