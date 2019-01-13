package com.infoshareacademy.jbusters.web.user;

import com.infoshareacademy.jbusters.authentication.CheckEmail;
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


@WebServlet(urlPatterns = "/acc-edit")
public class UserAccEditServlet extends HttpServlet {


    private static final Logger LOG = LoggerFactory.getLogger(UserAccEditServlet.class);
    private static final String TEMPLATE_NAME = "user-acc-edit";
    private static final String EMAIL_STATUS = "emailStatus";
    private static final String UPDATE_STATUS = "updateStatus";
    private static final String FAILED = "failed";
    private static final String SUCCESS = "success";

    @Inject
    private TemplateProvider templateProvider;
    @Inject
    private UserDao userDao;
    @Inject
    private CheckEmail checkEmail;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        resp.setContentType("text/html;charset=UTF-8");
        final PrintWriter out = resp.getWriter();
        Template template = templateProvider.getTemplate(getServletContext(), TEMPLATE_NAME);

        Map<String, Object> model = new HashMap<>();

        HttpSession session = req.getSession();
        User sessionUser = (User) session.getAttribute("user");


        model.put(UPDATE_STATUS, checkUpdateStatus(session));

        model.put("user", sessionUser);

        try {
            template.process(model, out);
        } catch (TemplateException e) {
            LOG.error("Failed to process user-acc-edit template due to: {}", e.getMessage());
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        HttpSession session = req.getSession();
        User sessionUser = (User) session.getAttribute("user");
        String sessionEmail = sessionUser.getUserEmail();
        User user = userDao.findByEmail(sessionEmail);
        String newEmail = req.getParameter("email");

        if (!newEmail.equals(sessionEmail)) {
            if (checkEmail.checkIfEmailCanBeEdited(newEmail, user.getUserId())) {
                user.setUserEmail(newEmail);
                LOG.info("User {} has changed his email to {}", sessionEmail, newEmail);
                session.setAttribute(EMAIL_STATUS, "emailSuccess");
            } else {
                session.setAttribute(UPDATE_STATUS, FAILED);
                session.setAttribute(EMAIL_STATUS, "emailExist");
                doGet(req, resp);
                return;
            }
        }

        user.setUserName(req.getParameter("name"));
        user.setUserSurname(req.getParameter("surname"));

        try {
            userDao.update(user);
            session.setAttribute("user", user);
            session.setAttribute(UPDATE_STATUS, SUCCESS);
            LOG.info("User {}, has changed his acc detials", sessionEmail);
            doGet(req, resp);
        } catch (Exception e) {
            LOG.error("Failed to update user {} account due to: {}", sessionEmail, e.getMessage());
            session.setAttribute(UPDATE_STATUS, FAILED);
            doGet(req, resp);
        }
    }

    private Map checkUpdateStatus(HttpSession session) {
        Map<String, String> model = new HashMap<>();

        String updateStatus = (String) session.getAttribute(UPDATE_STATUS);
        if (SUCCESS.equals(updateStatus)) {
            model.put("updateSuccess", "Zmiany zostały zapisane");
        } else if (FAILED.equals(updateStatus)) {
            model.put("updateFailed", "Nie udało się zapisać twoich zmian");
        }
        session.removeAttribute(UPDATE_STATUS);

        String emailStatus = (String) session.getAttribute(EMAIL_STATUS);
        if ("emailSuccess".equals(emailStatus)) {
            model.put("emailSuccess", "Twój Email został zmieniony!");
        } else if ("emailExist".equals(emailStatus)) {
            model.put("emailFailed", "Taki adres Email już istnieje!");
        }
        session.removeAttribute(EMAIL_STATUS);

        return model;
    }
}
