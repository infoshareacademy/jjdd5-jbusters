package com.infoshareacademy.jbusters.web.user;

import com.infoshareacademy.jbusters.dao.UserDao;
import com.infoshareacademy.jbusters.freemarker.TemplateProvider;
import com.infoshareacademy.jbusters.model.User;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

@WebServlet(urlPatterns = "/admin-users/editUser")
public class AdminUsersEditServlet extends HttpServlet {

    private static final Logger LOG = LoggerFactory.getLogger(AdminUsersServlet.class);
    private static final String TEMPLATE_NAME = "admin-users-edit";

    @Inject
    private TemplateProvider templateProvider;
    @Inject
    private UserDao userDao;
    @Inject
    private User sessionUser;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        resp.setContentType("text/html;charset=UTF-8");
        final PrintWriter out = resp.getWriter();

        Map<String, Object> model = new HashMap<>();

        HttpSession session = req.getSession();
        String sessionEmail = (String) session.getAttribute("userEmail");
        String sessionName = (String) session.getAttribute("userName");
        sessionUser = (User) session.getAttribute("user");

        model.put("sessionName", sessionName);
        model.put("sessionEmail", sessionEmail);
        model.put("sessionRole", sessionUser.getUserRole());

        int userId = Integer.parseInt(req.getParameter("id"));

        User user = userDao.findById(userId);

        model.put("user", user);

        Template template = templateProvider.getTemplate(getServletContext(), TEMPLATE_NAME);

        try {
            template.process(model, out);
        } catch (TemplateException e) {
            LOG.error("Faield to process model due to {}", e.getMessage());
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        int userId = Integer.parseInt(req.getParameter("id"));

        User user = userDao.findById(userId);

        String stringRole = req.getParameter("role");

        if (stringRole.equals("ADMIN")) {
            user.setUserRole(1);
        } else {
            user.setUserRole(2);
        }

        userDao.update(user);

        resp.sendRedirect("/admin-users");
    }
}
