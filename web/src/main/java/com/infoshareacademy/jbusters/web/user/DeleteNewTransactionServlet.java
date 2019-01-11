package com.infoshareacademy.jbusters.web.user;

import com.infoshareacademy.jbusters.authentication.Auth;
import com.infoshareacademy.jbusters.dao.NewTransactionDao;
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

@WebServlet("/delete-new-transaction")
public class DeleteNewTransactionServlet extends HttpServlet {

    private static final Logger LOG = LoggerFactory.getLogger(DeleteNewTransactionServlet.class);
    private static final String TEMPLATE_NAME = "user-delete-new-transaction";

    @Inject
    private TemplateProvider templateProvider;
    @Inject
    private NewTransactionDao newTransactionDao;
    @Inject
    private Auth auth;
    @Inject
    private User sessionUser;
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        resp.addHeader("Content-Type", "text/html; charset=utf-8");
        PrintWriter out = resp.getWriter();

        HttpSession session = req.getSession(true);
        String sessionName = (String) session.getAttribute("userName");
        String sessionEmail = (String) session.getAttribute("userEmail");
        sessionUser = (User) session.getAttribute("user");

        int transactionId = Integer.parseInt(req.getParameter("id"));

        if (auth.isUserAuthorizedToEdit(sessionEmail, transactionId)) {

            newTransactionDao.delete(transactionId);
            LOG.info("User {} deleted transaction with id {}", sessionEmail, transactionId);

            Template template = templateProvider.getTemplate(getServletContext(), TEMPLATE_NAME);

            Map<String, Object> model = new HashMap<>();
            model.put("sessionName", sessionName);
            model.put("sessionEmail", sessionEmail);
            model.put("sessionRole", sessionUser.getUserId());

            try {
                template.process(model, out);
                LOG.info("Delete ok");
            } catch (TemplateException e) {
                LOG.error("Delete failed");
            }
        } else {
            resp.sendRedirect("error.html");
            LOG.error("Unauthorized try of deletion by user {}, on transaction id {}", sessionEmail, transactionId);
        }
    }
}
