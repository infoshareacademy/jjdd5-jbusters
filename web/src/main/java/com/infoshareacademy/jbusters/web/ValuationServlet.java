package com.infoshareacademy.jbusters.web;

import com.infoshareacademy.jbusters.data.CalculatePrice;
import com.infoshareacademy.jbusters.data.FilterTransactions;
import com.infoshareacademy.jbusters.data.StatisticsManager;
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
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@WebServlet(urlPatterns = "/valuation")
public class ValuationServlet extends HttpServlet {

    private static final Logger LOG = LoggerFactory.getLogger(ValuationServlet.class);


    private static final String TEMPLATE_VALUATION = "valuation";
    private static final String TEMPLATE_SAVEINFO = "save-info";
    private static final String MARKET_TYPE = "market-type";

    @Inject
    private Transaction newTransaction;

    @Inject
    private TemplateProvider templateProvider;

    @Inject
    private FilterTransactions filterTransactions;

    @Inject
    private StatisticsManager statisticsManager;

    private NumericDataValidator numericDataValidator = new NumericDataValidator();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.addHeader("Content-Type", "text/html; charset=utf-8");

        RequestDispatcher requestDispatcher = req.getRequestDispatcher("/load-other-values");
        Map<String, Object> model = new HashMap<>();
        Map<String, String> errorsMap = saveTransactionDetails(req);
        model.put("errors", errorsMap);

        statisticsManager.captureNameFromServlet(req.getParameter("district"));

        List<Transaction> filteredList = filterTransactions.theGreatFatFilter(newTransaction);
        BigDecimal flatPrice = BigDecimal.valueOf(0);
        PrintWriter out = resp.getWriter();
        Template template = templateProvider.getTemplate(
                getServletContext(),
                TEMPLATE_VALUATION);


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

            if (req.getAttribute("constructionYearError") != null) {
                template = templateProvider.getTemplate(
                        getServletContext(), TEMPLATE_VALUATION);
                try {
                    template.process(model, out);
                } catch (TemplateException e) {
                    LOG.error("Failed to send model due to {}", e.getMessage());
                }
            } else {

                try {
                    template.process(model, out);
                } catch (TemplateException e) {
                    LOG.error("Failed to send model due to {}", e.getMessage());
                }
            }
        }
    }

//    @Override
//    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
//        resp.addHeader("Content-Type", "text/html; charset=utf-8");
//
//        RequestDispatcher requestDispatcher = req.getRequestDispatcher("/valuation");
//
//        Map<String, String> errorsMap = validateConstructionYear(req);
//        Map<String, Object> model = new HashMap<>();
//        PrintWriter out = resp.getWriter();
//        Template template = templateProvider.getTemplate(
//                getServletContext(), TEMPLATE_SAVEINFO);
//
//        newTransaction.setTransactionName(req.getParameter("description"));
//        newTransaction.setStreet(req.getParameter("street"));
//
//        String important = req.getParameter("important");
//
//        if ("nie".equals(important)) {
//            newTransaction.setImportant(false);
//        }
//        if ("tak".equals(important)) {
//            newTransaction.setImportant(true);
//        }
//
//        // TODO zamiast dispacher poszukać reload aby zostało na tej samoej stronie
//        // TODO nie pozwolić zapisać do pliku jeśli rok nie jest liczbą, lub zawiera litery
//
//
//        if (errorsMap.size() != 0) {
//
//            req.setAttribute("constructionYearError", errorsMap);
////                String url = req.getRequestURI();
////                resp.sendRedirect(url);
////                doGet(req, resp);
//            System.out.println(req.getRequestURI());
//            System.out.println(req.getRequestURL());
//            resp.sendRedirect(req.getRequestURI());
//        }
//
//        Menu menu = new Menu();
//        final Path path = Paths.get(System.getProperty("jboss.home.dir") + "/upload/flats.txt");
//
//        menu.saveTransaction(newTransaction, path, true);
//
//        String saved = "Zapisane";
//
//        model.put("price", saved);
//
//        try {
//            template.process(model, out);
//            LOG.info("Saved user transaction to file {}", path);
//        } catch (TemplateException e) {
//            LOG.error("Failed to save user file to {}", path);
//        }
//    }


    private Map<String, String> saveTransactionDetails(HttpServletRequest req) {
        Map<String, String> errorsMap = new HashMap<>();

        validateData(req, errorsMap);
        validateMarketType(req, errorsMap);

        newTransaction.setFlatArea(
                numericDataValidator.validate(req.getParameter("flat-area").replaceAll(",", "."),
                        errorsMap,
                        () -> BigDecimal.valueOf(Double.parseDouble(req.getParameter("flat-area").replaceAll(",", "."))),
                        "flatAreaError",
                        "Błąd podczas zapisu wielkości mieszkania!",
                        new BigDecimal(0)));

        newTransaction.setLevel(
                numericDataValidator.validate(req.getParameter("level"),
                        errorsMap,
                        () -> Integer.valueOf(req.getParameter("level")),
                        "levelError",
                        "Błąd podczas zapisu piętra!",
                        0));

        newTransaction.setConstructionYearCategory(
                numericDataValidator.validate(req.getParameter("construction"),
                        errorsMap,
                        () -> Integer.valueOf(req.getParameter("construction")),
                        "constructionYearError", "Zła kategoria roku budowy!", 0));

        return errorsMap;
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
        if (req.getParameter(MARKET_TYPE).equalsIgnoreCase("RYNEK WTÓRNY") ||
                req.getParameter(MARKET_TYPE).equalsIgnoreCase("RYNEK PIERWOTNY")) {
            try {
                newTransaction.setTypeOfMarket(req.getParameter(MARKET_TYPE).replaceAll("_", " "));
            } catch (Exception e) {
                LOG.error("Failed to save market type due to {}", e.getMessage());
                errorsMap.put("marketError", "Błąd podczas zapisu rodzaju rynku!");
            }
        } else {
            errorsMap.put("marketError", "Błąd podczas zapisu rodzaju rynku!");
        }
    }


}