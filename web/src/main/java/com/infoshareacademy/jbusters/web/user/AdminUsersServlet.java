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

@WebServlet(urlPatterns = "/admin-users")
public class AdminUsersServlet extends HttpServlet {


    private static final Logger LOG = LoggerFactory.getLogger(AdminUsersServlet.class);
    private static final String TEMPLATE_NAME = "admin-users";
    @Inject
    private TemplateProvider templateProvider;
    @Inject
    private UserDao userDao;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        resp.addHeader("Content-Type", "text/html; charset=utf-8");
        PrintWriter writter = resp.getWriter();
        Map<String, Object> model = new HashMap<>();

        HttpSession session = req.getSession(true);
        User sessionUser = (User) session.getAttribute("user");

        Template template = templateProvider.getTemplate(getServletContext(), TEMPLATE_NAME);
        model.put("user", sessionUser);

        List<User> usersList = userDao.findAll();

        model.put("usersList", usersList);
        model.put("size", usersList.size());

        try {
            template.process(model, writter);
        } catch (TemplateException e) {
            LOG.error("Failed to process model due to {}", e.getMessage());
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        int userId = Integer.parseInt(req.getParameter("id"));

        userDao.delete(userId);

        doGet(req, resp);
    }
}
