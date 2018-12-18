package com.infoshareacademy.jbusters.web;


import com.infoshareacademy.jbusters.data.SearchOfData;
import com.infoshareacademy.jbusters.freemarker.TemplateProvider;
import freemarker.template.Template;
import freemarker.template.TemplateException;

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

@WebServlet(urlPatterns = ("/load-district"))
public class LoadDistrictTransactionServlet extends HttpServlet {

    private static final String TEMPLATE_NAME = "load-district";

    @Inject
    private TemplateProvider templateProvider;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        resp.addHeader("Content-Type", "text/html; charset=utf-8");
        PrintWriter out = resp.getWriter();

        String city = req.getParameter("city");
        List<String> districtsList = new SearchOfData().showDistrict(city);

        Map<String, Object> model = new HashMap<>();
        model.put("city", city);
        model.put("district", districtsList);

        Template template = templateProvider.getTemplate(getServletContext(), TEMPLATE_NAME);

        try {
            template.process(model, out);
        } catch (TemplateException e) {
            e.printStackTrace();
        }


    }
}