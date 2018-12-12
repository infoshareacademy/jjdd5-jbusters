package com.infoshareacademy.jbusters.web;

import com.infoshareacademy.jbusters.console.Menu;
import com.infoshareacademy.jbusters.data.CalculatePrice;
import com.infoshareacademy.jbusters.data.FilterTransactions;
import com.infoshareacademy.jbusters.data.StatisticsManager;
import com.infoshareacademy.jbusters.data.Transaction;
import com.infoshareacademy.jbusters.freemarker.TemplateProvider;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
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
    private static final String TEMPLATE_NAME = "valuation";
    private static final String MARKET_TYPE = "market-type";

    private Transaction newTransaction = new Transaction();

    @Inject
    private TemplateProvider templateProvider;

    @Inject
    private FilterTransactions filterTransactions;

    @Inject
    private StatisticsManager statisticsManager;


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.addHeader("Content-Type", "text/html; charset=utf-8");

        Map<String, Object> model = new HashMap<>();
        Map<String, String> errorsMap = saveTransactionDetails(req);
        model.put("errors", errorsMap);

        statisticsManager.captureNameFromServlet(req.getParameter("district"));


        List<Transaction> filteredList = filterTransactions.theGreatFatFilter(newTransaction);
        BigDecimal flatPrice = BigDecimal.valueOf(0);
        PrintWriter out = resp.getWriter();
        Template template;
        template = templateProvider.getTemplate(
                getServletContext(),
                TEMPLATE_NAME);


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

        } else {
            template = templateProvider.getTemplate(
                    getServletContext(),
                    "no-valuation");
        }


        model.put("price", flatPrice);
        model.put("city", newTransaction.getCity());
        model.put("district1", newTransaction.getDistrict());
        model.put("market_type", newTransaction.getTypeOfMarket());
        model.put("flat_area", newTransaction.getFlatArea());
        model.put("level", newTransaction.getLevel());
        model.put("parking_spot", newTransaction.getParkingSpot());
        model.put("standard_level", newTransaction.getStandardLevel());
        model.put("construction", newTransaction.getConstructionYearCategory());

        try {
            template.process(model, out);
        } catch (TemplateException e) {
            LOG.error("Failed to send model due to {}", e.getMessage());
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        resp.addHeader("Content-Type", "text/html; charset=utf-8");
        PrintWriter out = resp.getWriter();

        newTransaction.setTransactionName(req.getParameter("description"));
        newTransaction.setStreet(req.getParameter("street"));
        newTransaction.setConstructionYear(req.getParameter("construction-year"));
        String important = req.getParameter("important");

        if ("nie".equals(important)){
            newTransaction.setImportant(false);
        }
        if ("tak".equals(important)){
            newTransaction.setImportant(true);
        }


        Menu menu = new Menu();
        final Path path = Paths.get(System.getProperty("jboss.home.dir") + "/upload/flats.txt");

        menu.saveTransaction(newTransaction, path, true);

        Template template = templateProvider.getTemplate(
                getServletContext(),
                "save-info");

        String saved = "Zapisane";

        Map<String, String> model = new HashMap<>();
        model.put("price", saved);

        try {
            template.process(model, out);
            LOG.info("Saved user transaction to file {}", path);
        } catch (TemplateException e) {
            LOG.error("Failed to save user file to {}", path);
        }
    }

    private Map<String, String> saveTransactionDetails (HttpServletRequest req) {
        Map<String, String> errorsMap = new HashMap<>();

        try {
            newTransaction.setTransactionDate(LocalDate.now());
            newTransaction.setCity(req.getParameter("city"));
            newTransaction.setDistrict(req.getParameter("district"));
            newTransaction.setParkingSpot(req.getParameter("parking-spot").replaceAll("_", " "));
            newTransaction.setStandardLevel(req.getParameter("standard-level").replaceAll("_", " "));
            newTransaction.setPrice(BigDecimal.valueOf(0));
            newTransaction.setPricePerM2(BigDecimal.valueOf(0));
        } catch (Exception e) {
            LOG.error("Failed to save user's due to: {}", e.getMessage());
            String errorMessage = e.getMessage();
            errorsMap.put("overalError", errorMessage);
        }

        if (req.getParameter(MARKET_TYPE).matches("RYNEK WTÓRNY") ||
                req.getParameter(MARKET_TYPE).matches("RYNEK PIERWOTNY")) {
            try {
                newTransaction.setTypeOfMarket(req.getParameter("market-type").replaceAll("_", " "));
            } catch (Exception e) {
                LOG.error("Failed to save market type due to {}", e.getMessage());
                errorsMap.put("marketError", "Błąd podczas zapisu rodzaju rynku!");
            }
        } else {
            errorsMap.put("marketError", "Błąd podczas zapisu rodzaju rynku!");
        }

        try {
            double flatArea = Double.parseDouble(req.getParameter("flat-area").replaceAll(",", "."));
            newTransaction.setFlatArea(BigDecimal.valueOf(flatArea));
        } catch (Exception e) {
            LOG.error("Failed to save flat size due to {}", e.getMessage());
            errorsMap.put("flatAreaError", "Błąd podczas zapisu wielkości mieszkania!");
        }
        try {
            newTransaction.setLevel(Integer.valueOf(req.getParameter("level")));
        } catch (Exception e) {
            LOG.error("Failed to save level due to {}", e.getMessage());
            errorsMap.put("levelError", "Błąd podczas zapisu piętra!");
        }
        try {
            newTransaction.setConstructionYearCategory(Integer.valueOf(req.getParameter("construction")));
        } catch (Exception e) {
            LOG.error("Failed to save construction year category due to {}", e.getMessage());
            errorsMap.put("dateError", "Zła kategoria roku budowy!");
        }
        return errorsMap;
    }
}
