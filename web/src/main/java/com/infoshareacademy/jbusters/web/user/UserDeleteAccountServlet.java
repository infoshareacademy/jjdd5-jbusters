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
import java.util.Map;

@WebServlet(urlPatterns = "/delete-account")
public class UserDeleteAccountServlet extends HttpServlet {
    private static final Logger LOG = LoggerFactory.getLogger(UserAccEditServlet.class);
    private static final String TEMPLATE_NAME = "user-delete-account";

    @Inject
    private TemplateProvider templateProvider;
    @Inject
    private UserDao userDao;

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        resp.setContentType("text/html;charset=UTF-8");
        final PrintWriter out = resp.getWriter();
        Template template = templateProvider.getTemplate(getServletContext(), TEMPLATE_NAME);


        Map<String, Object> model = new HashMap<>();

        HttpSession session = req.getSession();
        String sessionEmail = (String) session.getAttribute("userEmail");

        User user = userDao.findByEmail(sessionEmail);

        userDao.delete(user.getUserId());
        LOG.warn("Account ({}) has been deleted", sessionEmail);
        session.removeAttribute("userEmail");

        try {
            template.process(model, out);
        } catch (TemplateException e) {
            LOG.error("Failed to process account-deleted template due to {}", e.getMessage());
        }
    }
}
