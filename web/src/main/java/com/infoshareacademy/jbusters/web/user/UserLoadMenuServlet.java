package com.infoshareacademy.jbusters.web.user;


import com.infoshareacademy.jbusters.dao.UserDao;
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
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@WebServlet(urlPatterns = "/load-user-menu")
public class UserLoadMenuServlet extends HttpServlet {

    private static final Logger LOG = LoggerFactory.getLogger(UserLoadMenuServlet.class);
    private static final String TEMPLATE_NAME_LOGIN_ADMIN = "admin-login";
    private static final String TEMPLATE_NAME_LOGIN_USER = "user-login";
    private static final String TEMPLATE_NAME_SESSION_EXPIRED = "session-expired";

    @Inject
    private TemplateProvider templateProvider;
    @Inject
    private UserDao userDao;

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        resp.setContentType("text/html;charset=UTF-8");
        final PrintWriter writer = resp.getWriter();
        HttpSession session = req.getSession(true);
        User user;

        String sessionName = (String) session.getAttribute("userName");
        String sessionEmail = (String) session.getAttribute("userEmail");

        List<User> userList = userDao.findAll()
                .stream()
                .filter(u -> u.getUserEmail().equals(sessionEmail))
                .collect(Collectors.toList());

        Template template;
        Map<String, Object> model = new HashMap<>();

        if (!userList.isEmpty()) {
            user = userList.get(0);

            if (user.getUserRole() == 1) {
                template = templateProvider.getTemplate(getServletContext(), TEMPLATE_NAME_LOGIN_ADMIN);
            } else {
                template = templateProvider.getTemplate(getServletContext(), TEMPLATE_NAME_LOGIN_USER);
            }

        } else if (userList.isEmpty()) {
            template = templateProvider.getTemplate(getServletContext(), TEMPLATE_NAME_LOGIN_ADMIN);
        } else {
            template = templateProvider.getTemplate(getServletContext(), TEMPLATE_NAME_SESSION_EXPIRED);
        }

        model.put("sessionName", sessionName);
        model.put("sessionEmail", sessionEmail);

        try {
            LOG.info("Load user menu");
            template.process(model, writer);
        } catch (TemplateException e) {
            LOG.error("Failed load user menu");
        }


    }
}
