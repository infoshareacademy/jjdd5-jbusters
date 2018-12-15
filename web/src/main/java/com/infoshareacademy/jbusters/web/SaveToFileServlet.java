package com.infoshareacademy.jbusters.web;

import com.infoshareacademy.jbusters.console.Menu;
import com.infoshareacademy.jbusters.data.Transaction;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;


@WebServlet("/saveAs")
public class SaveToFileServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        Menu menu = new Menu();

        resp.setContentType("lista_mieszkan/txt");
        resp.setHeader("Content-Disposition", "attachment; filename=\"lista_mieszkan.txt\"");

        HttpSession session = req.getSession();
        List<Transaction> transactionList = (List<Transaction>) session.getAttribute("propertyList");
        OutputStream outputStream = resp.getOutputStream();

        try {
            for (Transaction transaction : transactionList) {
                String outString = menu.getTransactionAsString(transaction, true) + System.lineSeparator();
                outputStream.write(outString.getBytes());
            }
            outputStream.flush();
            outputStream.close();
        } catch (Exception e) {
            System.out.println(e.toString());
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        RequestDispatcher requestDispatcher = req.getRequestDispatcher("/my-flats");
        HttpSession session = req.getSession();

        session.removeAttribute("propertyList");
        requestDispatcher.forward(req, resp);
    }
}
