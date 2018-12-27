package com.infoshareacademy.jbusters.web.UsersServlet;

import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.transaction.Transactional;

import com.infoshareacademy.jbusters.dao.UsersDao;
import com.infoshareacademy.jbusters.freemarker.TemplateProvider;
import com.infoshareacademy.jbusters.model.Users;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collector;
import java.util.stream.Collectors;

@Transactional
@WebServlet(urlPatterns = "/add-user")
public class AddUsersServlet extends HttpServlet {


    private static final String TEMPLATE_SUCESS = "confirm-registration-sucess";
    private static final String TEMPLATE_FAILED = "confirm-registration-failed";

    private Logger LOG = LoggerFactory.getLogger(AddUsersServlet.class);

    @Inject
    private UsersDao usersDao;

    @Inject
    private TemplateProvider templateProvider;

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.addHeader("Content-Type", "text/html; charset=utf-8");

        PrintWriter out = resp.getWriter();
        final Users u = new Users();

        String email = req.getParameter("email");
        String password = req.getParameter("password");
        String password2 = req.getParameter("password2");
        List<Users> listUsers = usersDao.findAll();
        List<Users> emailList = listUsers.stream()
                .filter(e -> e.getUsersEmail().equals(email))
                .collect(Collectors.toList());

        if(emailList.isEmpty() && !email.isEmpty() && !password.isEmpty() && password.equals(password2)){

            String name = req.getParameter("name");
            String surname = req.getParameter("surname");
            u.setUsersEmail(email);
            u.setUsersPassword(password);
            u.setUsersName(name);
            u.setUsersSurname(surname);
            u.setUsersRole(2);

            usersDao.save(u);

            Map<String, Object> model = new HashMap<>();
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

        }else {
            Map<String, Object> model = new HashMap<>();
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

