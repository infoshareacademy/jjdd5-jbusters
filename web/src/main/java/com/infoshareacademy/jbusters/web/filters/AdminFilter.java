package com.infoshareacademy.jbusters.web.filters;


import com.infoshareacademy.jbusters.authentication.Auth;
import com.infoshareacademy.jbusters.model.User;
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
        urlPatterns = {"/admin-panel",
                "/admin-users/editUser",
                "/admin-users"
        })
public class AdminFilter implements Filter {
    private static final Logger LOG = LoggerFactory.getLogger(AdminFilter.class);

    private static final String INDEX_PAGE = "/index.html";

    @Inject
    private Auth auth;


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

        if (sessionUser != null) {
            if (auth.isAdmin(sessionUser.getUserEmail())) {
                filterChain.doFilter(servletRequest, servletResponse);
                LOG.info("User {} entered ADMIN page {}", sessionUser.getUserEmail(), reqUri);
            } else {
                resp.sendRedirect(INDEX_PAGE);
                LOG.error("Auth ERROR! User {} tried to enter ADMIN page: {}", sessionUser.getUserEmail(), reqUri);
            }
        } else {
            resp.sendRedirect(INDEX_PAGE);
            LOG.error("Auth ERROR! Unlogged user tried to enter ADMIN page: {} ", reqUri);
        }
    }

    @Override
    public void destroy() {

    }
}
