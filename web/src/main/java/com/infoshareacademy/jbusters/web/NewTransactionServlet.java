package com.infoshareacademy.jbusters.web;

import com.infoshareacademy.jbusters.data.CalculatePrice;
import com.infoshareacademy.jbusters.data.FilterTransactions;
import com.infoshareacademy.jbusters.data.Transaction;
import com.infoshareacademy.jbusters.freemarker.TemplateProvider;
import com.sun.xml.internal.ws.api.ha.StickyFeature;
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
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@WebServlet(urlPatterns = "/valuation")
public class NewTransactionServlet extends HttpServlet {

    private static final Logger LOG = LoggerFactory.getLogger(NewTransactionServlet.class);
    private static final String TEMPLATE_NAME = "new-transaction";

    @Inject
    private TemplateProvider templateProvider;

    @Inject
    private FilterTransactions filterTransactions;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.addHeader("Content-Type", "text/html; charset=utf-8");

        Transaction newTransaction = new Transaction();
        newTransaction.setTransactionDate(LocalDate.now());
        newTransaction.setCity(req.getParameter("city"));
        newTransaction.setDistrict(req.getParameter("district"));
        newTransaction.setTypeOfMarket(req.getParameter("market-type").replaceAll("_", " "));
        int flatArea = Integer.parseInt(req.getParameter("flat-area"));
        newTransaction.setFlatArea(BigDecimal.valueOf(flatArea));
        newTransaction.setLevel(Integer.valueOf(req.getParameter("level")));
        newTransaction.setParkingSpot(req.getParameter("parking-spot").replaceAll("_", " "));
        newTransaction.setStandardLevel(req.getParameter("standard-level").replaceAll("_", " "));
        newTransaction.setConstructionYearCategory(Integer.valueOf(req.getParameter("construction")));

        List<Transaction> filteredList = filterTransactions.theGreatFatFilter(newTransaction);
        CalculatePrice calc = new CalculatePrice(newTransaction, filteredList);
        PrintWriter out = resp.getWriter();

        BigDecimal flatPrice = calc.calculatePrice();

        Template template = templateProvider.getTemplate(
                getServletContext(),
                TEMPLATE_NAME);


        Map<String, Object> model = new HashMap<>();
        model.put("cena", flatPrice);

        try {
            template.process(model, out);
        } catch (TemplateException e) {
            e.printStackTrace();
        }

    }

}
