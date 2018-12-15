package com.infoshareacademy.jbusters.web;

import com.infoshareacademy.jbusters.data.ReportGenerator;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;

@WebServlet("/generate-report")
public class GenerateReportServlet extends HttpServlet {


    private static final String TEMPLATE_NAME = "load-other-values";
    private static final Logger LOG = LoggerFactory.getLogger(com.infoshareacademy.jbusters.web.LoadOtherValuesTransactionServlet.class);

    public static final String RAPORT_PATH = System.getProperty("jboss.server.temp.dir") + "/raport.pdf";
    @Inject
    private ReportGenerator reportGenerator;


    public GenerateReportServlet() throws FileNotFoundException {
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

       reportGenerator.generateReport();

        resp.setHeader("Content-Disposition", "attachment; filename=\"Raport.pdf\"");
        // reads input file from an absolute path
        File downloadFile = new File(RAPORT_PATH);
        FileInputStream inStream = new FileInputStream(downloadFile);


        // obtains ServletContext
        ServletContext context = getServletContext();

        // gets MIME type of the file
        String mimeType = context.getMimeType(RAPORT_PATH);
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

        Files.deleteIfExists(Paths.get(RAPORT_PATH));

    }



}