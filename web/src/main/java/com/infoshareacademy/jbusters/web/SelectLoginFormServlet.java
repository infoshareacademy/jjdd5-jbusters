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

@WebServlet(urlPatterns = ("/select-login-form"))
public class SelectLoginFormServlet extends HttpServlet {

    private static final Logger LOG = LoggerFactory.getLogger(SelectLoginFormServlet.class);
    private static final String TEMPLATE_NAME_GUEST = "select-login-form";
    private static final String TEMPLATE_NAME_LOGIN_ADMIN = "admin-login";
    private static final String TEMPLATE_NAME_LOGIN_OK = "user-login";
    private static final Integer ADMIN = 1;

    @Inject
    private TemplateProvider templateProvider;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.addHeader("Content-Type", "text/html; charset=utf-8");
        PrintWriter out = resp.getWriter();
        Template template;
        Map<String, Object> model = new HashMap<>();

        HttpSession session = req.getSession(true);
        User sessionUser = (User) session.getAttribute("user");
        model.put("user", sessionUser);

        if (sessionUser == null){
            template = templateProvider.getTemplate(getServletContext(), TEMPLATE_NAME_GUEST);
        } else {
            if (sessionUser.getUserRole() == ADMIN) {
                template = templateProvider.getTemplate(getServletContext(), TEMPLATE_NAME_LOGIN_ADMIN);
            } else {
                template = templateProvider.getTemplate(getServletContext(), TEMPLATE_NAME_LOGIN_OK);
            }
        }
        try {
            template.process(model, out);
            LOG.info("Loaded form login");
        } catch (TemplateException e) {
            LOG.error("Failed to load form login");
        }
    }
}
