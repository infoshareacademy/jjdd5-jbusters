package com.infoshareacademy.jbusters.web;

import com.infoshareacademy.jbusters.console.Menu;
import com.infoshareacademy.jbusters.data.CalculatePrice;
import com.infoshareacademy.jbusters.data.Data;
import com.infoshareacademy.jbusters.data.FilterTransactions;
import com.infoshareacademy.jbusters.data.StatisticsManager;
import com.infoshareacademy.jbusters.data.Transaction;
import com.infoshareacademy.jbusters.freemarker.TemplateProvider;
import com.sun.deploy.net.HttpRequest;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.apache.commons.lang3.StringUtils;
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
import java.math.BigDecimal;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;
import java.util.regex.Pattern;

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

        if (errorsMap.size() != 0) {
            try {
                req.setAttribute("errorsMap", errorsMap);
                requestDispatcher.forward(req, resp);
            } catch (ServletException e) {
                e.printStackTrace();
            }

        } else {


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
            model.put("listTransactionUseValuation", filteredList);

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
        Map<String, String> model = new HashMap<>();
        Template template = templateProvider.getTemplate(
                getServletContext(),
                "save-info");

        newTransaction.setTransactionName(req.getParameter("description"));
        newTransaction.setStreet(req.getParameter("street"));
        newTransaction.setConstructionYear(req.getParameter("construction"));
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

        String saved = "Zapisane";

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


    private Map<String, String> saveTransactionDetails(HttpServletRequest req) {
        Map<String, String> errorsMap = new HashMap<>();

        validateData(req, errorsMap);
        validateMarketType(req, errorsMap);

        newTransaction.setFlatArea(
                validateNumericData(req.getParameter("flat-area").replaceAll(",", "."),
                        errorsMap,
                        () -> BigDecimal.valueOf(Double.parseDouble(req.getParameter("flat-area").replaceAll(",", "."))),
                        "flatAreaError",
                        "Błąd podczas zapisu wielkości mieszkania!",
                        new BigDecimal(0)));

        newTransaction.setLevel(
                validateNumericData(req.getParameter("level"),
                        errorsMap,
                        () -> Integer.valueOf(req.getParameter("level")),
                        "levelError",
                        "Błąd podczas zapisu piętra!",
                        0));

        newTransaction.setConstructionYearCategory(
                validateNumericData(req.getParameter("construction"),
                        errorsMap,
                        () -> Integer.valueOf(req.getParameter("construction")),
                        "constructionYearError", "Zła kategoria roku budowy!", 0));

        return errorsMap;
    }

    private void validateData(HttpServletRequest req, Map<String, String> errorsMap) {
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
            errorsMap.put("overallError", errorMessage);
        }
    }

    private void validateMarketType(HttpServletRequest req, Map<String, String> errorsMap) {
        if (req.getParameter("market-type").equalsIgnoreCase("RYNEK WTÓRNY") ||
                req.getParameter("market-type").equalsIgnoreCase("RYNEK PIERWOTNY")) {
            try {
                newTransaction.setTypeOfMarket(req.getParameter("market-type").replaceAll("_", " "));
            } catch (Exception e) {
                LOG.error("Failed to save market type due to {}", e.getMessage());
                errorsMap.put("marketError", "Błąd podczas zapisu rodzaju rynku!");
            }
        } else {
            errorsMap.put("marketError", "Błąd podczas zapisu rodzaju rynku!");
        }
    }
// TODO CHECK WHY isNumeric returns false for values with dot ex. double
    private <T> T validateNumericData(String parameter, Map<String, String> errors, Supplier<T> supplier, String errorKey, String errorMessage, T defaultValue) {
        if (StringUtils.isNumeric(parameter) && !parameter.isEmpty()) {
            try {
                T value = supplier.get();
                return value;
            } catch (Exception e) {
                LOG.error(errorMessage, e);
                errors.put(errorKey, errorMessage);
                return defaultValue;
            }
        } else {
            errors.put(errorKey, errorMessage);
            return defaultValue;
        }
    }
}