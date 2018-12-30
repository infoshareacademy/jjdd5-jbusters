package com.infoshareacademy.jbusters.web.login;

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

@WebServlet(urlPatterns = {"/logout"})
public class LogoutServlet extends HttpServlet {

    private Logger LOG = LoggerFactory.getLogger(LogoutServlet.class);
    private static final String TEMPLATE_NAME = "user-logout";

    @Inject
    private TemplateProvider templateProvider;

    @Override
    protected void doGet (HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        resp.setContentType("text/html;charset=UTF-8");

        final PrintWriter writer = resp.getWriter();

            Map<String, Object> model = new HashMap<>();
            HttpSession session = req.getSession(true);
            session.invalidate();

            Template template = templateProvider.getTemplate(getServletContext(), TEMPLATE_NAME);

            try {
                template.process(model, writer);
                LOG.info("Logout user");
            } catch (TemplateException e) {
                LOG.error("Failed to logout user");
            }
    }
}