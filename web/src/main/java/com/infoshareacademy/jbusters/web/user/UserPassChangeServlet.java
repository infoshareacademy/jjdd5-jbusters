package com.infoshareacademy.jbusters.web.user;

import com.infoshareacademy.jbusters.authentication.Auth;
import com.infoshareacademy.jbusters.authentication.PasswordHashing;
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
import java.util.Map;

@WebServlet(urlPatterns = "/acc-edit/pass-change")

public class UserPassChangeServlet extends HttpServlet {

    private static final Logger LOG = LoggerFactory.getLogger(UserPassChangeServlet.class);
    private static final String TEMPLATE_NAME = "user-pass-change";

    @Inject
    private TemplateProvider templateProvider;
    @Inject
    private Auth auth;
    @Inject
    private UserDao userDao;
    @Inject
    private PasswordHashing passwordHashing;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        resp.setContentType("text/html;charset=UTF-8");
        final PrintWriter out = resp.getWriter();
        Template template = templateProvider.getTemplate(getServletContext(), TEMPLATE_NAME);

        Map<String, Object> model = new HashMap<>();

        HttpSession session = req.getSession();
        User sessionUser = (User) session.getAttribute("user");
        model.put("user", sessionUser);

        try {
            template.process(model, out);
        } catch (TemplateException e) {
            LOG.error("Failed to process model due to {}", e.getMessage());
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        resp.setContentType("text/html;charset=UTF-8");
        final PrintWriter out = resp.getWriter();
        Template template = templateProvider.getTemplate(getServletContext(), TEMPLATE_NAME);
        Map<String, Object> model = new HashMap<>();

        HttpSession session = req.getSession();
        User sessionUser = (User) session.getAttribute("user");
        String sessionEmail = sessionUser.getUserEmail();
        model.put("user", sessionUser);

        String oldPassword = req.getParameter("oldPassword");

        if (oldPassword != null && auth.checkCredensials(oldPassword, sessionEmail)) {
            String newPassword = req.getParameter("newPassword");
            String newPassword2 = req.getParameter("newPassword2");
            if (!newPassword.isEmpty() && newPassword.equals(newPassword2)) {
                User user = userDao.findByEmail(sessionEmail);
                user.setUserPassword(passwordHashing.generateHash(newPassword));
                userDao.update(user);
                model.put("passwordSuccess", "Twoje hasło zostało zmienione");
                LOG.info("User {} has updated his password", sessionEmail);
            } else {
                model.put("passwordFail", "Nowe hasło się nie zgadza!");
                LOG.info("User {} entered wrong password in check password box", sessionEmail);
            }
        } else {
            model.put("passwordFail", "Podałeś złe hasło!");
            LOG.warn("User {} entered wrong old password during password update process", sessionEmail);
        }

        try {
            template.process(model, out);
        } catch (TemplateException e) {
            LOG.error("Failed to process model due to {}", e.getMessage());
        }
    }
}
