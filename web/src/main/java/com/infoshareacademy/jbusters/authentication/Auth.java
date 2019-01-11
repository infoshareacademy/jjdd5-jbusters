package com.infoshareacademy.jbusters.authentication;

import com.infoshareacademy.jbusters.dao.NewTransactionDao;
import com.infoshareacademy.jbusters.dao.UserDao;
import com.infoshareacademy.jbusters.model.User;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;

@RequestScoped
public class Auth {

    @Inject
    private UserDao userDao;

    @Inject
    private NewTransactionDao newTransactionDao;

    public boolean isAdmin(String sessionEmail) {

        User user = userDao.findByEmail(sessionEmail);

        return user.getUserRole() == 1;
    }


    public boolean isUserAuthorizedToEdit(String sessionEmail, int transactionId) {

        User user = userDao.findByEmail(sessionEmail);

        return newTransactionDao.findByUser(user).stream()
                .anyMatch(newTransaction -> newTransaction.getNewTransactionId() == transactionId);

    }
}
