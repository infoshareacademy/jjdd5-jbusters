package com.infoshareacademy.jbusters.web;

import com.infoshareacademy.jbusters.data.ExchangeRatesManager;
import com.infoshareacademy.jbusters.freemarker.TemplateProvider;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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

    private static final String TEMPLATE_INDEX = "index";
    private static final Logger LOGGER = LoggerFactory.getLogger(ExchangeRatesServlet.class);

    @Inject
    ExchangeRatesManager exchangeRatesManager;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        exchangeRatesManager.updateExchangeRates();

        RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/admin-panel");
        dispatcher.forward(req, resp);
    }
}
