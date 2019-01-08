package com.infoshareacademy.jbusters.authentication;

import com.infoshareacademy.jbusters.dao.NewTransactionDao;
import com.infoshareacademy.jbusters.dao.UserDao;
import com.infoshareacademy.jbusters.model.NewTransaction;

import javax.faces.bean.RequestScoped;
import javax.inject.Inject;
import java.util.List;
import java.util.stream.Collectors;

@RequestScoped
public class AuthUser {

    @Inject
    private NewTransactionDao newTransactionDao;

    @Inject
    private UserDao userDao;

    public boolean isUserAuthorizedToEdit(String sessionEmail, int transactionId) {

        int userId = userDao.findUserIdByEmail(sessionEmail);

        List<NewTransaction> newTransactionList = newTransactionDao.findTransactionsByUserId(userId).stream()
                .filter(t -> t.getNewTransactionId() == transactionId)
                .collect(Collectors.toList());

        return newTransactionList.size() == 1;
    }
}
