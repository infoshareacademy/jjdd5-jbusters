package com.infoshareacademy.jbusters.web.filters;


import com.infoshareacademy.jbusters.authentication.AuthAdmin;
import com.infoshareacademy.jbusters.web.LoadCityTransactionServlet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebFilter(
        filterName = "AdminFilter",
        urlPatterns = {"/*"})
public class AdminFilter implements Filter {
    private static final Logger LOG = LoggerFactory.getLogger(AdminFilter.class);

    private static final String INDEX_PAGE = "/index";

//    private static final String EXCLUDED_ROOT_PATH = "/";
//    private static final String[] EXCLUDED_PATH_BEGINNINGS = new String[]{
//            WELCOME_PAGE_TO_REDIRECT,
//            "/id-token",
//            "/logout",
//            "/login",
//            "/dev-panel",
//            "/images",
//            "/create-hall",
//            "/error-handler"
//    };
//
//    private static final String[] EXCLUDED_PATH_ENDINGS = new String[]{
//            ".css",
//            ".js",
//            ".ico",
//            ".jpeg",
//            ".jpg",
//            ".png"
//    };

    @Inject
    private AuthAdmin authAdmin;


    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) servletRequest;
        HttpServletResponse resp = (HttpServletResponse) servletResponse;
        String reqUri = req.getRequestURI();

        HttpSession session = req.getSession(true);
        String sessionEmail = (String) session.getAttribute("userEmail");


        if (authAdmin.isAdmin(sessionEmail)) {
            LOG.info("User {} entered ADMIN page {}",sessionEmail, reqUri);
            filterChain.doFilter(servletRequest, servletResponse);
        } else {
            resp.sendRedirect(INDEX_PAGE);
            LOG.error("Auth ERROR! User {} tried to enter ADMIN page: {}", sessionEmail, reqUri);
        }
    }

    @Override
    public void destroy() {

    }
}
