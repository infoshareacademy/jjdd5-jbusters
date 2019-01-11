package com.infoshareacademy.jbusters.web.filters;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebFilter(filterName = "UserFilter",
        urlPatterns = {"/load-user-menu",
                "/edit-transaction",
                "/show-transaction",
                "/delete-new-transaction",
                "/add-transaction",
        })

public class UserFilter implements Filter {

    private static final Logger LOG = LoggerFactory.getLogger(UserFilter.class);

    private static final String INDEX_PAGE = "/index.html";

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

        if (sessionEmail != null) {
            filterChain.doFilter(servletRequest, servletResponse);
            LOG.info("User {} entered page {}", sessionEmail, reqUri);
        } else {
            resp.sendRedirect(INDEX_PAGE);
            LOG.warn("Unlogged user tried to enter user-acces page: {}", reqUri);
        }
    }

    @Override
    public void destroy() {

    }
}
