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
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@WebServlet(urlPatterns = "/valuation")
public class ValuationServlet extends HttpServlet {

    private static final Logger LOG = LoggerFactory.getLogger(ValuationServlet.class);


    private static final String TEMPLATE_VALUATION = "valuation";
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

                BigDecimal minimumPriceInList = calc.getMinimumPriceInList(filteredList).setScale(2, RoundingMode.HALF_UP);
                BigDecimal averagePriceInList = calc.getAvaragePriceInList(filteredList).setScale(2, RoundingMode.HALF_UP);
                BigDecimal maxPriceInList = calc.getMaxPriceInList(filteredList).setScale(2, RoundingMode.HALF_UP);

                model.put("minimumPrice", minimumPriceInList);
                model.put("averagePrice", averagePriceInList);
                model.put("maxPrice", maxPriceInList);
                model.put("listTransactionUseValuation", filteredList);

                flatPrice = calc.calculatePrice().setScale(2, RoundingMode.HALF_UP);

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

            String cityName = req.getParameter("city");
            String districtName = req.getParameter("district");
            statisticsManager.captureNameFromServlet(cityName, districtName, flatPrice.setScale(2, BigDecimal.ROUND_UP).toString());

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
        String marketType = req.getParameter(MARKET_TYPE);
        if (marketType == null) {
            return;
        }
        if (marketType.equalsIgnoreCase("RYNEK WTÓRNY") ||
                marketType.equalsIgnoreCase("RYNEK PIERWOTNY")) {
            try {
                newTransaction.setTypeOfMarket(marketType.replaceAll("_", " "));
            } catch (Exception e) {
                LOG.error("Failed to save market type due to {}", e.getMessage());
                errorsMap.put("marketError", "Błąd podczas zapisu rodzaju rynku!");
            }
        } else {
            errorsMap.put("marketError", "Błąd podczas zapisu rodzaju rynku!");
        }
    }
}