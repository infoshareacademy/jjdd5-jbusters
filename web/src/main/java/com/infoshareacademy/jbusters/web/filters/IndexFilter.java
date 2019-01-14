package com.infoshareacademy.jbusters.web.filters;

import com.infoshareacademy.jbusters.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebFilter(filterName = "IndexFilter",
        urlPatterns = {"/index.html",
                "add-user-form",
                "select-login-form",
                "login-form"})
public class IndexFilter implements Filter {

    private static final Logger LOG = LoggerFactory.getLogger(UserFilter.class);
    private static final String INDEX_PAGE = "/load-user-menu";


    @Override
    public void init(FilterConfig filterConfig) {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) servletRequest;
        HttpServletResponse resp = (HttpServletResponse) servletResponse;
        String reqUri = req.getRequestURI();

        HttpSession session = req.getSession(true);
        User sessionUser = (User) session.getAttribute("user");

        if (sessionUser == null) {
            filterChain.doFilter(servletRequest, servletResponse);
        } else {
            resp.sendRedirect(INDEX_PAGE);
            LOG.info("Redirecting user {} from {} page to menu", sessionUser.getUserEmail(), reqUri);
        }
    }

    @Override
    public void destroy() {

    }
}
