package com.infoshareacademy.jbusters.web.user;

import com.infoshareacademy.jbusters.data.ExchangeRatesManager;
import com.infoshareacademy.jbusters.data.MailScheduler;
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
import java.util.Map;

@WebServlet(urlPatterns = "/admin-panel")
public class AdminPanelServlet extends HttpServlet {

    private static final Logger LOG = LoggerFactory.getLogger(AdminPanelServlet.class);
    private static final String TEMPLATE_NAME = "admin-panel";
    private static final String SENT_STATUS = "sentStatus";
    private static final String SCHEDULE_STATUS = "scheduleStatus";

    @Inject
    private TemplateProvider templateProvider;

    @Inject
    private ExchangeRatesManager exchangeRatesManager;

    @Inject
    private MailScheduler mailScheduler;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.addHeader("Content-Type", "text/html; charset=utf-8");
        PrintWriter writter = resp.getWriter();
        Map<String, Object> model = new HashMap<>();

        HttpSession session = req.getSession(true);
        User sessionUser = (User) session.getAttribute("user");
        model.put("user", sessionUser);
        model.put("exRatesMap", exchangeRatesManager.getExRatesMap());
        model.put("exRatesMapSize", exchangeRatesManager.getExRatesMap().size());
        model.put("exRatesMapDate", exchangeRatesManager.getLastModifiedDate());
        model.put("exRatesURL", exchangeRatesManager.getURL());
        model.put("exRatesGetCurrent", exchangeRatesManager.getExchangeRate());
        model.put("isSchedulerOn", mailScheduler.isSchedulerOn());
        model.put("setDate", mailScheduler.getSetDateString());

        Template template = templateProvider.getTemplate(getServletContext(), TEMPLATE_NAME);

        if (req.getAttribute(SENT_STATUS) == null) {
            model.put(SENT_STATUS, "");
        } else {
            String sentStatus = req.getAttribute(SENT_STATUS).toString();
            model.put(SENT_STATUS, sentStatus);
        }

        if (req.getAttribute(SCHEDULE_STATUS) == null) {
            model.put(SCHEDULE_STATUS, "");
        } else {
            String scheduleStatus = req.getAttribute(SCHEDULE_STATUS).toString();
            model.put(SCHEDULE_STATUS, scheduleStatus);
        }

        try {
            LOG.info("Load admin panel");
            template.process(model, writter);
        } catch (TemplateException e) {
            LOG.error("Failed load admin panel");
        }
    }
}
