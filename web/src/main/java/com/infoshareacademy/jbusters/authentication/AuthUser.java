package com.infoshareacademy.jbusters.authentication;

import com.infoshareacademy.jbusters.dao.NewTransactionDao;
import com.infoshareacademy.jbusters.dao.UserDao;
import com.infoshareacademy.jbusters.model.User;

import javax.faces.bean.RequestScoped;
import javax.inject.Inject;

@RequestScoped
public class AuthUser {

    @Inject
    private NewTransactionDao newTransactionDao;

    @Inject
    private UserDao userDao;

    public boolean isUserAuthorizedToEdit(String sessionEmail, int transactionId) {

        User user = userDao.findByEmail(sessionEmail);

        return newTransactionDao.findByUser(user).stream()
                .anyMatch(newTransaction -> newTransaction.getNewTransactionId() == transactionId);

    }
}
