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

@WebServlet("/selectexrate")
public class ExchangeRatesSelectorServlet extends HttpServlet {

    @Inject
    ExchangeRatesManager exchangeRatesManager;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {

        String code = req.getParameter("code");
        String value = req.getParameter("value");

        exchangeRatesManager.updateExchangeRates();
        exchangeRatesManager.setExchangeRate(code, value);

        RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/admin-panel");
        dispatcher.forward(req, resp);
    }
}
