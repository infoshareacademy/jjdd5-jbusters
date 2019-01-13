package com.infoshareacademy.jbusters.web.user;


import com.infoshareacademy.jbusters.dao.SuggestionsDao;
import com.infoshareacademy.jbusters.freemarker.TemplateProvider;
import com.infoshareacademy.jbusters.model.Suggestions;
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

@WebServlet(urlPatterns = ("/load-suggestions"))
public class ShowSuggestionsServlet extends HttpServlet {

    private static final Logger LOG = LoggerFactory.getLogger(ShowSuggestionsServlet.class);
    private static final String TEMPLATE_NAME = "admin-suggestions";

    @Inject
    private SuggestionsDao suggestionsDao;

    @Inject
    private TemplateProvider templateProvider;


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.addHeader("Content-Type", "text/html; charset=utf-8");
        PrintWriter out = resp.getWriter();

        Template template = templateProvider.getTemplate(getServletContext(), TEMPLATE_NAME);
        Map<String, Object> model = new HashMap<>();
        HttpSession session = req.getSession(true);
        User sessionUser = (User) session.getAttribute("user");
        model.put("user", sessionUser);

        List<Suggestions> suggestionsList = suggestionsDao.findAll();
        model.put("suggestions", suggestionsList);
        model.put("size", suggestionsList.size());

        try {
            template.process(model, out);
            LOG.info("Load suggestions list, ok");
        } catch (TemplateException e) {
            LOG.error("Failed to load suggestions list", e.getMessage());
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int suggestionsId = Integer.parseInt(req.getParameter("id"));

        suggestionsDao.delete(suggestionsId);

        doGet(req, resp);
    }
}
