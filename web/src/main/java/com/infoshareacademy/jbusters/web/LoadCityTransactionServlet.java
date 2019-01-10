package com.infoshareacademy.jbusters.web;

import com.infoshareacademy.jbusters.data.SearchOfData;
import com.infoshareacademy.jbusters.data.StaticFields;
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
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@WebServlet(urlPatterns = "/load-city")
public class LoadCityTransactionServlet extends HttpServlet {

    private static final String TEMPLATE_NAME_GUEST = "load-city";
    private static final String TEMPLATE_NAME_USER = "user-load-city";
    private static final Logger LOG = LoggerFactory.getLogger(LoadCityTransactionServlet.class);

    @Inject
    private TemplateProvider templateProvider;
    @Inject
    private SearchOfData searchOfData;

    @Inject
    private StaticFields staticFieldsManager;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {


        resp.addHeader("Content-Type", "text/html; charset=utf-8");
        PrintWriter out = resp.getWriter();

        List<String> cities = searchOfData.showCity();
        Map<String, Object> model = new HashMap<>();
        model.put("cities", cities);

        HttpSession session = req.getSession(true);
        String sessionEmail = (String) session.getAttribute("userEmail");
        String sessionName = (String) session.getAttribute("userName");

        if (sessionEmail == null){
            Template template = templateProvider.getTemplate(getServletContext(), TEMPLATE_NAME_GUEST);

            try {
                template.process(model, out);
                LOG.info("Loaded city for list of size {}", cities.size());
            } catch (TemplateException e) {
                LOG.error("Failed to load city list. Size of list: {}", cities.size());
            }
        } else {

            Template template = templateProvider.getTemplate(getServletContext(), TEMPLATE_NAME_USER);
            model.put("sessionEmail", sessionEmail);
            model.put("sessionName", sessionName);


            try {
                template.process(model, out);
                LOG.info("Loaded city for {}. List of size {}", sessionEmail, cities.size());
            } catch (TemplateException e) {
                LOG.error("Failed to load city for {}. List. Size of list: {}",sessionEmail, cities.size());
            }
        }

    }
}
