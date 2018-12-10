package com.infoshareacademy.jbusters.web;

import com.infoshareacademy.jbusters.console.Menu;
import com.infoshareacademy.jbusters.data.CalculatePrice;
import com.infoshareacademy.jbusters.data.Data;
import com.infoshareacademy.jbusters.data.FilterTransactions;
import com.infoshareacademy.jbusters.data.Transaction;
import com.infoshareacademy.jbusters.freemarker.TemplateProvider;
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
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@WebServlet(urlPatterns = "/valuation")
public class ValuationServlet extends HttpServlet {

    private static final Logger LOG = LoggerFactory.getLogger(ValuationServlet.class);
    private static String TEMPLATE_NAME = "valuation";

    private Transaction newTransaction = new Transaction();

    @Inject
    private TemplateProvider templateProvider;

    @Inject
    private FilterTransactions filterTransactions;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.addHeader("Content-Type", "text/html; charset=utf-8");

        newTransaction.setTransactionDate(LocalDate.now());
        newTransaction.setCity(req.getParameter("city"));
        newTransaction.setDistrict(req.getParameter("district"));
        newTransaction.setTypeOfMarket(req.getParameter("market-type").replaceAll("_", " "));
        double flatArea = Double.parseDouble(req.getParameter("flat-area").replaceAll(",", "."));
        newTransaction.setFlatArea(BigDecimal.valueOf(flatArea));
        newTransaction.setLevel(Integer.valueOf(req.getParameter("level")));
        newTransaction.setParkingSpot(req.getParameter("parking-spot").replaceAll("_", " "));
        newTransaction.setStandardLevel(req.getParameter("standard-level").replaceAll("_", " "));
        newTransaction.setConstructionYearCategory(Integer.valueOf(req.getParameter("construction")));
        newTransaction.setPrice(BigDecimal.valueOf(0));
        newTransaction.setPricePerM2(BigDecimal.valueOf(0));

        List<Transaction> filteredList = filterTransactions.theGreatFatFilter(newTransaction);
        BigDecimal flatPrice = BigDecimal.valueOf(0);
        PrintWriter out = resp.getWriter();
        Template template;
        template = templateProvider.getTemplate(
                getServletContext(),
                TEMPLATE_NAME);

        Map<String, Object> model = new HashMap<>();

        if (filteredList.size() >= 11) {
            CalculatePrice calc = new CalculatePrice(newTransaction, filteredList);
            BigDecimal yearlyTrendOfPriceChange = calc.overallTrend(filteredList);
            model.put("trend", yearlyTrendOfPriceChange);

            BigDecimal minimumPriceInList = calc.getMinimumPriceInList(filteredList);
            BigDecimal averagePriceInList = calc.getAvaragePriceInList(filteredList);
            BigDecimal maxPriceInList = calc.getMaxPriceInList(filteredList);

            model.put("minimumPrice", minimumPriceInList);
            model.put("averagePrice", averagePriceInList);
            model.put("maxPrice", maxPriceInList);

            flatPrice = calc.calculatePrice();

        }else{
            template = templateProvider.getTemplate(
                    getServletContext(),
                    "no-valuation");
        }


        model.put("cena", flatPrice);

        try {
            template.process(model, out);
        } catch (TemplateException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        newTransaction.setTransactionName(("NOWA"));
        newTransaction.setStreet("Kartuska");
        newTransaction.setConstructionYear("2010");

        resp.addHeader("Content-Type", "text/html; charset=utf-8");
        PrintWriter out = resp.getWriter();
        Menu menu = new Menu();
        final Path path = Paths.get(System.getProperty("jboss.home.dir") + "/upload/flats.txt");

        menu.saveTransaction(newTransaction, path, true);

        Template template = templateProvider.getTemplate(
                getServletContext(),
                TEMPLATE_NAME);

        String plik = "Zapisane";

        Map<String, String> model = new HashMap<>();
        model.put("cena", plik);

        try {
            template.process(model, out);
            LOG.info("Saved user transaction to file {}", path);
        } catch (TemplateException e) {
            LOG.error("Failed to save user file to {}", path);
        }
    }
}
