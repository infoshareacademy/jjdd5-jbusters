package com.infoshareacademy.jbusters.web;

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
import java.util.HashMap;
import java.util.Map;

@WebServlet(urlPatterns = ("/load-other-values"))
public class LoadOtherValuesTransactionServlet extends HttpServlet {

    private static final String TEMPLATE_NAME = "load-other-values";
    private static final Logger LOG = LoggerFactory.getLogger(LoadOtherValuesTransactionServlet.class);

    @Inject
    private TemplateProvider templateProvider;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        resp.addHeader("Content-Type", "text/html; charset=utf-8");
        PrintWriter out = resp.getWriter();

        String city = req.getParameter("city");
        String district = req.getParameter("district");


        Map<String, Object> model = new HashMap<>();
        model.put("city", city);
        model.put("district1", district);

        Template template = templateProvider.getTemplate(getServletContext(), TEMPLATE_NAME);

        try {
            template.process(model, out);
            LOG.info("Sending city of {} and district of {} to template", city, district);
        } catch (TemplateException e) {
            LOG.error("Failed to export data to tamplate {} {}", city, district);
        }
    }
}