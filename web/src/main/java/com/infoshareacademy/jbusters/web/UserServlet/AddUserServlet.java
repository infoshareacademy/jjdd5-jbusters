package com.infoshareacademy.jbusters.web.UserServlet;

import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.transaction.Transactional;

import com.infoshareacademy.jbusters.dao.UserDao;
import com.infoshareacademy.jbusters.freemarker.TemplateProvider;
import com.infoshareacademy.jbusters.model.User;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Transactional
@WebServlet(urlPatterns = "/add-user")
public class AddUserServlet extends HttpServlet {


    private static final String TEMPLATE_SUCESS = "confirm-registration-sucess";
    private static final String TEMPLATE_FAILED = "confirm-registration-failed";
    private static final Logger LOG = LoggerFactory.getLogger(AddUserServlet.class);

    @Inject
    private UserDao userDao;

    @Inject
    private TemplateProvider templateProvider;

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.addHeader("Content-Type", "text/html; charset=utf-8");

        PrintWriter out = resp.getWriter();
        final User u = new User();

        String email = req.getParameter("email");
        String password = req.getParameter("password");
        String password2 = req.getParameter("password2");
        List<User> listUsers = userDao.findAll();
        List<User> emailList = listUsers.stream()
                .filter(e -> e.getUserEmail().equals(email))
                .collect(Collectors.toList());

        setDataTemplate(req, out, u, email, password, password2, emailList);

    }

    private void setDataTemplate(HttpServletRequest req, PrintWriter out, User u, String email, String password, String password2, List<User> emailList) throws IOException {

        Map<String, Object> model = new HashMap<>();

        if (emailList.isEmpty() && !email.isEmpty() && !password.isEmpty() && password.equals(password2)) {

            String name = req.getParameter("name");
            String surname = req.getParameter("surname");
            u.setUserEmail(email);
            u.setUserPassword(password);
            u.setUserName(name);
            u.setUserSurname(surname);
            u.setUserRole(2);

            userDao.save(u);

            model.put("email", email);
            model.put("name", name);
            model.put("surname", surname);
            Template template = templateProvider.getTemplate(getServletContext(), TEMPLATE_SUCESS);

            try {
                template.process(model, out);
                LOG.info("confirm-registration!! all ok");
            } catch (TemplateException e) {
                LOG.error("Failed confirm-registration!!");
            }

        } else {
            Template template = templateProvider.getTemplate(getServletContext(), TEMPLATE_FAILED);
            try {
                template.process(model, out);
                LOG.info("confirm-registration!!no email ");
            } catch (TemplateException e) {
                LOG.error("Failed confirm-registration!! no email");
            }
        }
    }


}

