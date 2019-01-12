package com.infoshareacademy.jbusters.web;

import com.infoshareacademy.jbusters.console.Menu;
import com.infoshareacademy.jbusters.dao.NewTransactionDao;
import com.infoshareacademy.jbusters.dao.UserDao;
import com.infoshareacademy.jbusters.data.Transaction;
import com.infoshareacademy.jbusters.model.NewTransaction;
import com.infoshareacademy.jbusters.model.User;
import com.infoshareacademy.jbusters.parsing.NewTransactionParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
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

    @Inject
    private UserDao userDao;
    @Inject
    private NewTransactionDao newTransactionDao;
    @Inject
    private NewTransactionParser newTransactionParser;


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        Menu menu = new Menu();

        resp.setContentType("lista_mieszkan/csv");
        resp.setHeader("Content-Disposition", "attachment; filename=\"lista_mieszkan.csv\"");

        HttpSession session = req.getSession();
        List<Transaction> transactionList = (List<Transaction>) session.getAttribute("propertyList");
        OutputStream outputStream = resp.getOutputStream();

        try {
            for (Transaction transaction : transactionList) {
                String outString = menu.getTransactionAsString(transaction, true) + System.lineSeparator();
                outputStream.write(outString.getBytes(Charset.forName("UTF-8")));
            }
            outputStream.flush();
            outputStream.close();
        } catch (Exception e) {
            LOG.error("Failed to save list of flats and send it to user due to {}", e.getMessage());
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        resp.setContentType("lista_mieszkan/csv");
        resp.setHeader("Content-Disposition", "attachment; filename=\"lista_mieszkan.csv\"");
        HttpSession session = req.getSession();
        String sessionEmail = (String) session.getAttribute("userEmail");
        OutputStream outputStream = resp.getOutputStream();

        User user = userDao.findByEmail(sessionEmail);
        List<NewTransaction> newTransactionList = newTransactionDao.findByUser(user);

        try {
            for (NewTransaction newTransaction : newTransactionList) {
                String outString = newTransactionParser.parseToCsv(newTransaction);
                outputStream.write(outString.getBytes(Charset.forName("UTF-8")));
            }
            outputStream.flush();
            outputStream.close();
        } catch (Exception e) {
            LOG.error("Failed to save list of flats and send it to user due to {}", e.getMessage());
        }
    }
}
