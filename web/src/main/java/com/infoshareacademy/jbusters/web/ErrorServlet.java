package com.infoshareacademy.jbusters.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/error")
public class ErrorServlet extends HttpServlet {

    private static final Logger LOG = LoggerFactory.getLogger(ErrorServlet.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        Throwable throwable = (Throwable) req.getAttribute("javax.servlet.error.exception");
        if (throwable != null) {
            LOG.error("Error encountered: " + throwable.getMessage());
        }

        resp.sendRedirect("/error.html");
    }
}
