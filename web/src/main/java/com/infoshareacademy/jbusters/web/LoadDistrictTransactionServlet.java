package com.infoshareacademy.jbusters.web;


import com.infoshareacademy.jbusters.data.SearchOfData;
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

@WebServlet(urlPatterns = ("/load-district"))
public class LoadDistrictTransactionServlet extends HttpServlet {

    private static final String TEMPLATE_NAME_GUEST = "load-district";
    private static final String TEMPLATE_NAME_USER = "user-load-district";
    private static final Logger LOG = LoggerFactory.getLogger(LoadDistrictTransactionServlet.class);

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

        HttpSession session = req.getSession(true);
        Object sessionEmail =  session.getAttribute("userEmail");
        Object sessionName =  session.getAttribute("userName");


        if(sessionEmail == null){
            Template template = templateProvider.getTemplate(getServletContext(), TEMPLATE_NAME_GUEST);

            try {
                template.process(model, out);
                LOG.info("Loaded district list of size {}", districtsList.size());
            } catch (TemplateException e) {
                LOG.error("Failed to load district list. Size of list: {}" + districtsList.size());
            }
        }else {

            Template template = templateProvider.getTemplate(getServletContext(), TEMPLATE_NAME_USER);
            model.put("sessionEmail", sessionEmail);
            model.put("sessionName", sessionName);


            try {
                template.process(model, out);
                LOG.info("Loaded district for {}, List of size {}", sessionEmail, districtsList.size());
            } catch (TemplateException e) {
                LOG.error("Failed to load district for {}. List size: {}", sessionEmail, districtsList.size());
            }
        }


    }
}
