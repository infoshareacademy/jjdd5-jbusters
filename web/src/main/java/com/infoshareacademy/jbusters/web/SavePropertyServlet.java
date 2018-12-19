package com.infoshareacademy.jbusters.web;

import com.infoshareacademy.jbusters.data.Transaction;
import com.infoshareacademy.jbusters.freemarker.TemplateProvider;
import com.infoshareacademy.jbusters.web.validator.NumericDataValidator;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@WebServlet("/save-property")
public class SavePropertyServlet extends HttpServlet {

    private static final Logger LOG = LoggerFactory.getLogger(ValuationServlet.class);
    private static final String TEMPLATE_SAVEINFO = "save-info";

    @Inject
    private TemplateProvider templateProvider;

    private NumericDataValidator numericDataValidator = new NumericDataValidator();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.addHeader("Content-Type", "text/html; charset=utf-8");

        HttpSession session = req.getSession();
        Transaction newTransaction = (Transaction) session.getAttribute("newTransaction");

        List<Transaction> newTransactionList = new ArrayList<>();
        newTransactionList.add(newTransaction);

        List<Transaction> userTlist = newTransactionList.stream()
                .map(Transaction::new)
                .collect(Collectors.toList());

        Transaction userT = userTlist.get(0);

        RequestDispatcher requestDispatcher = req.getRequestDispatcher("/error.html");

        Map<String, String> errorsMap = validateConstructionYear(req, userT);
        Map<String, Object> model = new HashMap<>();
        PrintWriter out = resp.getWriter();
        Template template = templateProvider.getTemplate(
                getServletContext(), TEMPLATE_SAVEINFO);

        userT.setTransactionName(req.getParameter("description"));
        userT.setStreet(req.getParameter("street"));

        String important = req.getParameter("important");

        if ("nie".equals(important)) {
            userT.setImportant(false);
        }
        if ("tak".equals(important)) {
            userT.setImportant(true);
        }

        if (errorsMap.size() != 0) {
            req.setAttribute("constructionYearError", errorsMap);
            requestDispatcher.forward(req, resp);
        } else {

            List<Transaction> propertyList = (List<Transaction>) session.getAttribute("propertyList");
            if (propertyList == null) {
                propertyList = new ArrayList<>();
                session.setAttribute("propertyList", propertyList);
            }
            propertyList.add(userT);

            String saved = "Twoje mieszkanie zostało dodane do listy";
            model.put("saved", saved);

            try {
                template.process(model, out);
                LOG.info("User transaction saved to session, number of flats in sesion: {}", propertyList.size());
            } catch (TemplateException e) {
                LOG.error("Failed to save user flat");
            }
        }
    }

    private Map<String, String> validateConstructionYear(HttpServletRequest req, Transaction userT) {
        Map<String, String> errorsMap = new HashMap<>();
        userT.setConstructionYear(
                numericDataValidator.validate(req.getParameter("construction-year"),
                        errorsMap,
                        () -> req.getParameter("construction-year"),
                        "constructionYear",
                        "Popraw rok budowy!",
                        "0"));
        return errorsMap;
    }
}
