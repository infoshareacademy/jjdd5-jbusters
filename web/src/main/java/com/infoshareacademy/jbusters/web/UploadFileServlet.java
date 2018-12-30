package com.infoshareacademy.jbusters.web;

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
import java.util.Map;

@WebServlet(urlPatterns = "/upload-file")
public class UploadFileServlet extends HttpServlet {

    private static final String TEMPLATE_NAME = "user-upload-file";
    private static final Logger LOG = LoggerFactory.getLogger(UploadFileServlet.class);

    @Inject
    private TemplateProvider templateProvider;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        resp.addHeader("Content-Type", "text/html; charset=utf-8");
        PrintWriter out = resp.getWriter();


        Map<String, Object> model = new HashMap<>();


        HttpSession session = req.getSession(true);
        Object sessionEmail =  session.getAttribute("userEmail");
        Object sessionName =  session.getAttribute("userName");


        Template template = templateProvider.getTemplate(getServletContext(), TEMPLATE_NAME);
        model.put("sessionEmail", sessionEmail);
        model.put("sessionName", sessionName);

            try {
                template.process(model, out);
                LOG.info("Loaded file");
            } catch (TemplateException e) {
                LOG.error("Failed to load file");
            }

        }
    }
