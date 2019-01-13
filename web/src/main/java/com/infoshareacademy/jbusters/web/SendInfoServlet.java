package com.infoshareacademy.jbusters.web;

import com.infoshareacademy.jbusters.freemarker.TemplateProvider;
import com.infoshareacademy.jbusters.model.User;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

@WebServlet(urlPatterns = "/send-info")
public class SendInfoServlet extends HttpServlet {

    private static final Logger LOG = LoggerFactory.getLogger(SendInfoServlet.class);
    private static final String TEMPLATE_SEND_INFO = "send-info";
    @Inject
    private TemplateProvider templateProvider;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.addHeader("Content-Type", "text/html; charset=utf-8");
        PrintWriter out = resp.getWriter();
        Template template = templateProvider.getTemplate(getServletContext(), TEMPLATE_SEND_INFO);
        Map<String, Object> model = new HashMap<>();
        HttpSession session = req.getSession(true);
        User sessionUser = (User) session.getAttribute("user");
        model.put("user", sessionUser);

        try {
            template.process(model, out);
        } catch (TemplateException e) {
            LOG.error("Failed to process template due to {}", e.getMessage());
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.addHeader("Content-Type", "text/html; charset=utf-8");
        PrintWriter out = resp.getWriter();
        Template template = templateProvider.getTemplate(getServletContext(), TEMPLATE_SEND_INFO);
        Map<String, Object> model = new HashMap<>();
        HttpSession session = req.getSession(true);
        User sessionUser = (User) session.getAttribute("user");
        model.put("user", sessionUser);

        String city = req.getParameter("city");
        String district = req.getParameter("district");

        if (city.isEmpty() && district.isEmpty()) {
            model.put("fail", "Musisz wypełnić przynajmniej jedno pole");
        } else {
            model.put("success", "Twoja wiadomość została wysłana");
        }

        try {
            template.process(model, out);
        } catch (TemplateException e) {
            LOG.error("Failed to process template due to {}", e.getMessage());
        }
    }
}
