package com.infoshareacademy.jbusters.web;

import com.infoshareacademy.jbusters.data.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.util.List;


@WebServlet("/saveAs")
public class SaveToFileServlet extends HttpServlet {

    private static final Logger LOG = LoggerFactory.getLogger(SaveToFileServlet.class);


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        resp.setContentType("lista_mieszkan/csv");
        resp.setHeader("Content-Disposition", "attachment; filename=\"lista_mieszkan.csv\"");

        HttpSession session = req.getSession();
        List<Transaction> transactionList = (List<Transaction>) session.getAttribute("propertyList");
        OutputStream outputStream = resp.getOutputStream();

        try {
            for (Transaction transaction : transactionList) {
                String outString = transaction.toStringIsUserFileOption(true) + System.lineSeparator();
                outputStream.write(outString.getBytes(Charset.forName("UTF-8")));
            }
            outputStream.flush();
            outputStream.close();
        } catch (Exception e) {
            LOG.error("Failed to save list if flats and send it to user due to {}", e.getMessage());
        }
    }
}
