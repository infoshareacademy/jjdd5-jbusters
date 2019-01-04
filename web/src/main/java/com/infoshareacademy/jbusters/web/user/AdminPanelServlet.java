package com.infoshareacademy.jbusters.web.user;

import com.infoshareacademy.jbusters.freemarker.TemplateProvider;
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

@WebServlet(urlPatterns = "/admin-panel")
public class AdminPanelServlet extends HttpServlet {


    private static final Logger LOG = LoggerFactory.getLogger(AdminPanelServlet.class);
    private static final String TEMPLATE_NAME = "admin-panel";
    @Inject
    private TemplateProvider templateProvider;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.addHeader("Content-Type", "text/html; charset=utf-8");
        PrintWriter writter = resp.getWriter();

        HttpSession session = req.getSession(true);
        String sessionEmail = (String) session.getAttribute("userEmail");
        String sessionName = (String) session.getAttribute("userName");

        Map<String, Object> model = new HashMap<>();
        Template template = templateProvider.getTemplate(getServletContext(), TEMPLATE_NAME);
        model.put("sessionEmail", sessionEmail);
        model.put("sessionName", sessionName);

        try {
            LOG.info("Load admin panel");
            template.process(model, writter);
        } catch (TemplateException e) {
            LOG.error("Failed load admin panel");
        }


    }
}
