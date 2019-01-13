package com.infoshareacademy.jbusters.web;

import com.infoshareacademy.jbusters.data.Transaction;
import com.infoshareacademy.jbusters.freemarker.TemplateProvider;
import com.infoshareacademy.jbusters.model.User;
import freemarker.template.Template;
import freemarker.template.TemplateException;

import javax.inject.Inject;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
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
    private static final String TEMPLATE_LOGGED_USERS_TRANSACTION = "user-transactions-users";

    @Inject
    private TemplateProvider templateProvider;
    @Inject
    private User sessionUser;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType("text/html;charset=UTF-8");

        final PrintWriter writer = resp.getWriter();
        Map<String, Object> model = new HashMap<>();

        HttpSession session = req.getSession();
        User sessionUser = (User) session.getAttribute("user");

        List<Transaction> propertyList = (List<Transaction>) session.getAttribute("propertyList");


        Template template;

        if (sessionUser == null){
            template = templateProvider.getTemplate(getServletContext(), TEMPLATE_USERS_TRANSACTION);
        } else {
            template = templateProvider.getTemplate(getServletContext(), TEMPLATE_LOGGED_USERS_TRANSACTION);

            model.put("user", sessionUser);
        }

        if (propertyList == null || propertyList.isEmpty()) {
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

        HttpSession session = req.getSession();

        session.removeAttribute("propertyList");
        doGet(req, resp);
    }
}
