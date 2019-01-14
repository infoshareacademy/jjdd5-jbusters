package com.infoshareacademy.jbusters.web;

import com.infoshareacademy.jbusters.dao.TranzactionDao;
import com.infoshareacademy.jbusters.freemarker.TemplateProvider;
import com.infoshareacademy.jbusters.model.User;
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
    private TranzactionDao tranzactionDao;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {


        resp.addHeader("Content-Type", "text/html; charset=utf-8");
        PrintWriter out = resp.getWriter();

        List<String> cities = tranzactionDao.getCitiesList();
        Map<String, Object> model = new HashMap<>();
        model.put("cities", cities);

        HttpSession session = req.getSession(true);
        User sessionUser = (User) session.getAttribute("user");
        model.put("user", sessionUser);

        if (sessionUser == null) {
            Template template = templateProvider.getTemplate(getServletContext(), TEMPLATE_NAME_GUEST);
            try {
                template.process(model, out);
                LOG.info("Loaded city for list of size {}", cities.size());
            } catch (TemplateException e) {
                LOG.error("Failed to load city list. Size of list: {}", cities.size());
            }
        } else {
            Template template = templateProvider.getTemplate(getServletContext(), TEMPLATE_NAME_USER);
            try {
                template.process(model, out);
                LOG.info("Loaded city for {}. List of size {}", sessionUser.getUserEmail(), cities.size());
            } catch (TemplateException e) {
                LOG.error("Failed to load city for {}. List. Size of list: {}", sessionUser.getUserEmail(), cities.size());
            }
        }
    }
}
