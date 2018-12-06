package com.infoshareacademy.jbusters.web;

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
import java.util.Map;

@WebServlet(urlPatterns = "/new-transaction")
public class NewTransactionServlet extends HttpServlet {

    private static final Logger LOG = LoggerFactory.getLogger(NewTransactionServlet.class);
    private static final String TEMPLATE_NAME = "new-transaction";

    @Inject
    private TemplateProvider templateProvider;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.addHeader("Content-Type", "text/html; charset=utf-8");
        PrintWriter out = resp.getWriter();

        Map<String, Object> model = new HashMap<>();
        model.put("new-transaction", "transaction");

        Template template = templateProvider.getTemplate(getServletContext(), TEMPLATE_NAME);
        try {
            LOG.info("testsssss");
            template.process(model, out);
        } catch (TemplateException e) {
            LOG.error("Error while processing template: " + e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        Transaction transaction = new Transaction();
    }

}
