package com.infoshareacademy.jbusters.web.translations;

import com.infoshareacademy.jbusters.data.LanguageManager;

import javax.inject.Inject;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/language")
public class LanguageSelectorServlet extends HttpServlet {

    @Inject
    private LanguageManager languageManager;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {

        String language = req.getParameter("lang");

        languageManager.setLanguage(language);

        RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/index.html");
        dispatcher.forward(req, resp);
    }
}
