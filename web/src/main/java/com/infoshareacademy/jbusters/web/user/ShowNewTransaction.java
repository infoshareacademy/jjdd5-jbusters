package com.infoshareacademy.jbusters.web.user;


import com.infoshareacademy.jbusters.dao.NewTransactionDao;
import com.infoshareacademy.jbusters.dao.UserDao;
import com.infoshareacademy.jbusters.freemarker.TemplateProvider;
import com.infoshareacademy.jbusters.model.NewTransaction;
import com.infoshareacademy.jbusters.model.User;
import freemarker.template.Template;
import freemarker.template.TemplateException;

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

@WebServlet("/show-transaction")
public class ShowNewTransaction extends HttpServlet {

    private static final String TEMPLATE_NAME = "user-show-new-transaction";

    @Inject
    private TemplateProvider templateProvider;

    @Inject
    private UserDao userDao;

    @Inject
    private NewTransactionDao newTransactionDao;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        resp.setContentType("text/html;charset=UTF-8");

        final PrintWriter writer = resp.getWriter();
        Map<String, Object> model = new HashMap<>();

        HttpSession session = req.getSession();
        String sessionEmail = (String) session.getAttribute("userEmail");
        String sessionName = (String) session.getAttribute("userName");


        List<User> listUsers = userDao.findAll();
        List<User> emailList = listUsers.stream()
                .filter(e -> e.getUserEmail().equals(sessionEmail))
                .collect(Collectors.toList());

        int userId = emailList.get(0).getUserId();

        List<NewTransaction> newTransactionList = newTransactionDao.findAll();

        List<NewTransaction> userTransaction  = newTransactionList.stream()
                .filter(t -> t.getNewTransactionUserId().getUserId() == userId)
                .collect(Collectors.toList());

        Template template;
        template = templateProvider.getTemplate(getServletContext(), TEMPLATE_NAME);

        model.put("sessionEmail", sessionEmail);
        model.put("sessionName", sessionName);
        model.put("trans", userTransaction);
        model.put("size", userTransaction.size());

        try {
            template.process(model, writer);
        } catch (TemplateException e) {
            e.printStackTrace();
        }
    }
}
