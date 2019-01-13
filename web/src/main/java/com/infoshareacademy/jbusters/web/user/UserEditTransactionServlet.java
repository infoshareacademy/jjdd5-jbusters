package com.infoshareacademy.jbusters.web.user;

import com.infoshareacademy.jbusters.authentication.Auth;
import com.infoshareacademy.jbusters.dao.NewTransactionDao;
import com.infoshareacademy.jbusters.freemarker.TemplateProvider;
import com.infoshareacademy.jbusters.model.NewTransaction;
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

@WebServlet(urlPatterns = "/edit-transaction")
public class UserEditTransactionServlet extends HttpServlet {

    private static final Logger LOG = LoggerFactory.getLogger(UserEditTransactionServlet.class);
    private static final String TEMPLATE_EDIT_TRANSACTION = "user-edit-transaction";

    @Inject
    private TemplateProvider templateProvider;
    @Inject
    private Auth auth;
    @Inject
    private NewTransactionDao newTransactionDao;


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        resp.setContentType("text/html;charset=UTF-8");
        final PrintWriter out = resp.getWriter();

        Map<String, Object> model = new HashMap<>();

        HttpSession session = req.getSession();
        User sessionUser = (User) session.getAttribute("user");
        String sessionEmail = sessionUser.getUserEmail();
        model.put("user", sessionUser);

        int transactionId = Integer.parseInt(req.getParameter("id"));

        if (auth.isUserAuthorizedToEdit(sessionEmail, transactionId)) {
            NewTransaction transactionToEdit = newTransactionDao.findById(transactionId);

            model.put("transactionId", transactionId);
            model.put("city", transactionToEdit.getNewTransactionCity());
            model.put("district", transactionToEdit.getNewTransactionDistrict());
            model.put("marketType", transactionToEdit.getNewTransactionTypeOfMarket());
            model.put("flatArea", transactionToEdit.getNewTransactionFlatArea());
            model.put("level", transactionToEdit.getNewTransactionLevel());
            model.put("parkingSpot", transactionToEdit.getNewTransactionParkingSpot());
            model.put("standardLevel", transactionToEdit.getNewTransactionStandardLevel());
            model.put("construction", transactionToEdit.getNewTransactionConstructionYearCategory());
            model.put("important", transactionToEdit.isNewTransactionImportant());
            model.put("sale", transactionToEdit.getNewTransactionSale());

            Template template = templateProvider.getTemplate(getServletContext(), TEMPLATE_EDIT_TRANSACTION);

            try {
                template.process(model, out);
            } catch (TemplateException e) {
                LOG.error("Failed to procees template due to {}", e.getMessage());
            }
        } else {
            resp.sendRedirect("error.html");
            LOG.error("Unauthorized try to edit transaction with id: {}, by user {}", transactionId, sessionEmail);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType("text/html;charset=UTF-8");

        HttpSession session = req.getSession();
        User sessionUser = (User) session.getAttribute("user");
        String sessionEmail = sessionUser.getUserEmail();
        int transactionId = Integer.parseInt(req.getParameter("id"));

        if (auth.isUserAuthorizedToEdit(sessionEmail, transactionId)) {
            NewTransaction transactionToEdit = newTransactionDao.findById(transactionId);

            if (req.getParameter("important") != null) {
                if (req.getParameter("important").equals("tak")) {
                    transactionToEdit.setNewTransactionImportant(true);
                } else {
                    transactionToEdit.setNewTransactionImportant(false);
                }
            }

            if (req.getParameter("sale").equals("tak") || req.getParameter("sale").equals("nie")) {
                transactionToEdit.setNewTransactionSale(req.getParameter("sale"));
            }

            newTransactionDao.update(transactionToEdit);

            resp.sendRedirect("show-transaction");

        } else {
            resp.sendRedirect("error.html");
            LOG.error("Unauthorized try to edit transaction with id: {}, by user {}", transactionId, sessionEmail);
        }
    }
}
