package com.infoshareacademy.jbusters.authentication;

import com.infoshareacademy.jbusters.dao.UserDao;
import com.infoshareacademy.jbusters.model.User;

import javax.faces.bean.RequestScoped;
import javax.inject.Inject;
import java.util.List;

@RequestScoped
public class CheckEmail {

    @Inject
    private UserDao userDao;

    public boolean checkIfEmailCanBeEdited (String email, int userId) {

        List<User> userList = userDao.findByEmailList(email);

        if (!userList.isEmpty()) {
            return userList.get(0).getUserId() == userId;
        } else {
            return true;
        }
    }
}
