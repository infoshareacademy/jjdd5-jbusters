package com.infoshareacademy.jbusters.web.user;

import com.infoshareacademy.jbusters.dao.NewTransactionDao;
import com.infoshareacademy.jbusters.data.Transaction;
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
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

@WebServlet("/add-transaction")
public class AddNewTransactionServlet extends HttpServlet {

    private static final Logger LOG = LoggerFactory.getLogger(AddNewTransactionServlet.class);
    private static final String TEMPLATE_NAME = "user-save-transaction-ok";

    @Inject
    private TemplateProvider templateProvider;
    @Inject
    private NewTransactionDao newTransactionDao;
    @Inject
    private User sessionUser;
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        resp.addHeader("Content-Type", "text/html; charset=utf-8");
        PrintWriter out = resp.getWriter();
        HttpSession session = req.getSession();
        Transaction newTransaction = (Transaction) session.getAttribute("newTransaction");
        User user = (User) session.getAttribute("user");
        BigDecimal priceM2 = (BigDecimal) session.getAttribute("priceM2");
        BigDecimal price = (BigDecimal) session.getAttribute("price");

        NewTransaction transaction = new NewTransaction();

        transaction.setNewTransactionUser(user);
        transaction.setNewTransactionSale("nie");
        transaction.setNewTransactionDescription(req.getParameter("description"));
        transaction.setNewTransactionImportant(req.getParameter("important"));
        transaction.setNewTransactionDataTransaction(LocalDate.now());
        transaction.setNewTransactionCity(newTransaction.getCity());
        transaction.setNewTransactionDistrict(newTransaction.getDistrict());
        transaction.setNewTransactionStreet(req.getParameter("street"));
        transaction.setNewTransactionTypeOfMarket(newTransaction.getTypeOfMarket());
        transaction.setNewTransactionPrice(price);
        transaction.setNewTransactionFlatArea(newTransaction.getFlatArea());
        transaction.setNewTransactionPricePerM2(priceM2);
        transaction.setNewTransactionLevel(newTransaction.getLevel());
        transaction.setNewTransactionParkingSpot(newTransaction.getParkingSpot());
        transaction.setNewTransactionStandardLevel(newTransaction.getStandardLevel());
        transaction.setNewTransactionConstructionYear(newTransaction.getConstructionYear());
        transaction.setNewTransactionConstructionYearCategory(newTransaction.getConstructionYearCategory());

        try {
            newTransactionDao.save(transaction);
            Template template = templateProvider.getTemplate(getServletContext(), TEMPLATE_NAME);
            String sessionName = (String) session.getAttribute("userName");
            String sessionEmail = (String) session.getAttribute("userEmail");
            sessionUser = (User) session.getAttribute("user");
            Map<String, Object> model = new HashMap<>();
            model.put("sessionName", sessionName);
            model.put("sessionEmail", sessionEmail);
            model.put("sessionRole", sessionUser.getUserId());
            try {
                template.process(model, out);
                LOG.info("Save ok");
            } catch (TemplateException e) {
                LOG.error("Save failed");
            }
        } catch (Exception e) {
            LOG.error("Failed to save user's transaction to DB due to: {}", e.getMessage());
            resp.sendRedirect("/error.html");
        }
    }
}
