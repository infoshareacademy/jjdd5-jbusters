package com.infoshareacademy.jbusters.web.translations;

import com.infoshareacademy.jbusters.data.LanguageManager;
import com.infoshareacademy.jbusters.freemarker.TemplateProvider;
import com.infoshareacademy.jbusters.model.User;
import freemarker.template.Template;
import freemarker.template.TemplateException;

import javax.inject.Inject;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@WebServlet(urlPatterns = "index.html")
public class IndexServlet extends HttpServlet {

    private static final String TEMPLATE_INDEX = "index";
    private static final String TEMPLATE_USER_INDEX = "index";
    private static final String MENU_VALUATION = "menu_valuation";
    private static final String MENU_UPLOAD = "menu_upload";
    private static final String MENU_MY_APARTMENTS = "menu_my_apartments";
    private static final String MENU_CREATE_ACCOUNT = "menu_create_account";
    private static final String MENU_LOGIN = "menu_login";
    private static final String HOME_HEADLINE = "home_headline";
    private static final String HOME_SUBHEADLINE = "home_subheadline";
    private static final String HOME_BUTTON_VALUATION = "home_button_valuation";

    @Inject
    private TemplateProvider templateProvider;
    @Inject
    private LanguageManager languageManager;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        resp.addHeader("Content-Type", "text/html; charset=utf-8");
        HttpSession session = req.getSession();
        String sessionEmail = (String) session.getAttribute("userEmail");
        String sessionName = (String) session.getAttribute("userName");

        Map<String, Object> model = new HashMap<>();
        modelHandler(model, MENU_VALUATION);
        modelHandler(model, MENU_UPLOAD);
        modelHandler(model, MENU_MY_APARTMENTS);
        modelHandler(model, MENU_CREATE_ACCOUNT);
        modelHandler(model, MENU_LOGIN);
        modelHandler(model, HOME_HEADLINE);
        modelHandler(model, HOME_SUBHEADLINE);
        modelHandler(model, HOME_BUTTON_VALUATION);

        model.put("sessionName", sessionName);
        model.put("sessionEmail", sessionEmail);

        Template template = templateProvider.getTemplate(
                getServletContext(), TEMPLATE_INDEX);

        try {
            template.process(model, resp.getWriter());
        } catch (TemplateException e) {
            e.printStackTrace();
        }
    }

    private void modelHandler(Map<String, Object> model, String keyName) throws IOException {
        model.put(keyName, languageManager.translate(keyName));
    }
}
