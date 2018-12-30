package com.infoshareacademy.jbusters.web;

import com.infoshareacademy.jbusters.data.Transaction;
import com.infoshareacademy.jbusters.freemarker.TemplateProvider;
import freemarker.template.Template;
import freemarker.template.TemplateException;

import javax.inject.Inject;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@WebServlet("/my-flats")
public class MyFlatsServlet extends HttpServlet {

    private static final String TEMPLATE_USERS_TRANSACTION = "transactions-users";

    @Inject
    private TemplateProvider templateProvider;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType("text/html;charset=UTF-8");

        final PrintWriter writer = resp.getWriter();
        Map<String, Object> model = new HashMap<>();

        HttpSession session = req.getSession();
        List<Transaction> propertyList = (List<Transaction>) session.getAttribute("propertyList");

        Template template = templateProvider.getTemplate(
                getServletContext(),
                TEMPLATE_USERS_TRANSACTION);

        if (propertyList == null) {
            String error = "Twoja lista jest pusta";
            model.put("error", error);
        }

        model.put("flats", propertyList);
        try {
            template.process(model, writer);
        } catch (TemplateException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        doGet(req,resp);
    }
}
