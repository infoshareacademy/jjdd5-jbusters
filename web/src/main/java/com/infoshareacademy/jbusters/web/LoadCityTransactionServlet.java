package com.infoshareacademy.jbusters.web;

import com.infoshareacademy.jbusters.data.Data;
import com.infoshareacademy.jbusters.data.DataLoader;
import com.infoshareacademy.jbusters.data.SearchOfData;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@WebServlet(urlPatterns = "/load-city")
public class LoadCityTransactionServlet extends HttpServlet {

    private static final String TEMPLATE_NAME = "load-city";
    private static final Logger LOG = LoggerFactory.getLogger(ValuationServlet.class);

    @Inject
    private TemplateProvider templateProvider;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        resp.addHeader("Content-Type", "text/html; charset=utf-8");
        PrintWriter out = resp.getWriter();

        List<String> cities = new SearchOfData().showCity();

        Map<String, Object> model = new HashMap<>();
        model.put("cities", cities);

        Template template = templateProvider.getTemplate(getServletContext(), TEMPLATE_NAME);

        try {
            template.process(model, out);
            LOG.info("Loaded city list of size {}", cities.size());
        } catch (TemplateException e) {
            e.printStackTrace();
            LOG.error("Failed to load city list. Size of list: {}", cities.size());
        }
    }
}
