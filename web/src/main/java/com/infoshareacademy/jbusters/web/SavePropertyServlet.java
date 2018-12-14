package com.infoshareacademy.jbusters.web;

import com.infoshareacademy.jbusters.console.Menu;
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
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

@WebServlet("/save-property")
public class SavePropertyServlet extends HttpServlet {

    private static final Logger LOG = LoggerFactory.getLogger(ValuationServlet.class);
    private static final String TEMPLATE_VALUATION = "valuation";
    private static final String TEMPLATE_SAVEINFO = "save-info";
    private static final String MARKET_TYPE = "market-type";

    @Inject
    private Transaction newTransaction = new Transaction();

    @Inject
    private TemplateProvider templateProvider;

   private NumericDataValidator numericDataValidator = new NumericDataValidator();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        resp.addHeader("Content-Type", "text/html; charset=utf-8");

        RequestDispatcher requestDispatcher = req.getRequestDispatcher("/valuation");

        Map<String, String> errorsMap = validateConstructionYear(req);
        Map<String, Object> model = new HashMap<>();
        PrintWriter out = resp.getWriter();
        Template template = templateProvider.getTemplate(
                getServletContext(), TEMPLATE_SAVEINFO);

        newTransaction.setTransactionName(req.getParameter("description"));
        newTransaction.setStreet(req.getParameter("street"));

        String important = req.getParameter("important");

        if ("nie".equals(important)) {
            newTransaction.setImportant(false);
        }
        if ("tak".equals(important)) {
            newTransaction.setImportant(true);
        }

        // TODO zamiast dispacher poszukać reload aby zostało na tej samoej stronie
        // TODO nie pozwolić zapisać do pliku jeśli rok nie jest liczbą, lub zawiera litery


        if (errorsMap.size() != 0) {
            req.setAttribute("constructionYearError", errorsMap);
            requestDispatcher.forward(req, resp);
        } else {

            Menu menu = new Menu();
            final Path path = Paths.get(System.getProperty("jboss.home.dir") + "/upload/flats.txt");

            menu.saveTransaction(newTransaction, path, true);

            String saved = "Zapisane";

            model.put("price", saved);

            try {
                template.process(model, out);
                LOG.info("Saved user transaction to file {}", path);
            } catch (TemplateException e) {
                LOG.error("Failed to save user file to {}", path);
            }
        }
    }

    private Map<String, String> validateConstructionYear(HttpServletRequest req) {
        Map<String, String> errorsMap = new HashMap<>();
        newTransaction.setConstructionYear(
                numericDataValidator.validate(req.getParameter("construction-year"),
                        errorsMap,
                        () -> req.getParameter("construction-year"),
                        "constructionYear",
                        "Popraw rok budowy!",
                        "0"));
        return errorsMap;
    }
}
