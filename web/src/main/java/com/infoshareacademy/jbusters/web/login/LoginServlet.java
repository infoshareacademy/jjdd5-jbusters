package com.infoshareacademy.jbusters.web.login;

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
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


@WebServlet(urlPatterns = {"/login"})
public class LoginServlet extends HttpServlet {

    private static final Logger LOG = LoggerFactory.getLogger(LoginServlet.class);
    private static final String TEMPLATE_NAME = "user-login";

    @Inject
    private TemplateProvider templateProvider;



    @Override
    protected void doPost (HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        resp.setContentType("text/html;charset=UTF-8");

        final PrintWriter writer = resp.getWriter();

        try {
            LOG.info("Login user with google api");

            String idToken = req.getParameter("id_token");
            GoogleIdToken.Payload payLoad = IdTokenVerifierAndParser.getPayload(idToken);
            String name = (String) payLoad.get("name");
            String email = payLoad.getEmail();

            Map<String, Object> model = new HashMap<>();

            HttpSession session = req.getSession(true);
            session.setAttribute("userName", name);
            session.setAttribute("userEmail", email);

            String sessionName = (String) session.getAttribute("userName");
            String sessionEmail = (String) session.getAttribute("userEmail");

            model.put("sessionName", sessionName);
            model.put("sessionEmail", sessionEmail);

            Template template = templateProvider.getTemplate(getServletContext(), TEMPLATE_NAME);

            setDataTemplate(writer, model, sessionName, template);

        } catch (Exception e) {
            LOG.error("Failed to login user in google api");
        }
    }

    private void setDataTemplate(PrintWriter writer, Map<String, Object> model, Object sessionName, Template template) throws IOException {
        try {
            template.process(model, writer);
            LOG.info("Login user {}", sessionName);
        } catch (TemplateException e) {
            LOG.error("Failed to login user");
        }
    }
}
