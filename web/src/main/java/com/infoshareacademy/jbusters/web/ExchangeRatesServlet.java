package com.infoshareacademy.jbusters.web;

import com.infoshareacademy.jbusters.data.ExchangeRatesManager;

import javax.inject.Inject;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/exrates")
public class ExchangeRatesServlet extends HttpServlet {

    @Inject
    ExchangeRatesManager exchangeRatesManager;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        exchangeRatesManager.updateExchangeRates();

        RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/admin-panel");
        dispatcher.forward(req, resp);
    }
}
