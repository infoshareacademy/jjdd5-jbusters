package com.infoshareacademy.jbusters.authentication;

import com.infoshareacademy.jbusters.dao.UserDao;
import com.infoshareacademy.jbusters.model.User;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;

@RequestScoped
public class AuthAdmin {

    @Inject
    private UserDao userDao;

    public boolean isAdmin(String sessionEmail) {

        User user = userDao.findByEmail(sessionEmail);

        return user.getUserRole() == 1;

    }
}
