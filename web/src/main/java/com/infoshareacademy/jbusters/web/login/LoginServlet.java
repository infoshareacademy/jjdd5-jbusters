package com.infoshareacademy.jbusters.web.login;

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.infoshareacademy.jbusters.freemarker.TemplateProvider;
import com.infoshareacademy.jbusters.web.UsersServlet.AddUsersServlet;
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

    private Logger LOG = LoggerFactory.getLogger(LoginServlet.class);
    private static final String TEMPLATE_NAME = "login";

    @Inject
    private TemplateProvider templateProvider;



    @Override
    protected void doPost (HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        resp.setContentType("text/html;charset=UTF-8");

        final PrintWriter writer = resp.getWriter();

        try {
            String idToken = req.getParameter("id_token");
            GoogleIdToken.Payload payLoad = IdTokenVerifierAndParser.getPayload(idToken);
            String name = (String) payLoad.get("name");
            String email = payLoad.getEmail();

            System.out.println("User name: " + name);
            System.out.println("User email: " + email);

            Map<String, Object> model = new HashMap<>();

            HttpSession session = req.getSession(true);
            session.setAttribute("userName", name);
            session.setAttribute("userEmail", email);

            Object sessionName =  session.getAttribute("userName");
            Object sessionEmail =  session.getAttribute("userEmail");



            model.put("sessionName", sessionName);
            model.put("sessionEmail", sessionEmail);


            Template template = templateProvider.getTemplate(getServletContext(), TEMPLATE_NAME);

            try {
                template.process(model, writer);
                LOG.info("Login user {}", sessionName);
            } catch (TemplateException e) {
                LOG.error("Failed to login user");
            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
