package com.infoshareacademy.jbusters.authentication;

import org.mindrot.jbcrypt.BCrypt;

import javax.faces.bean.RequestScoped;

@RequestScoped
public class PasswordHashing {

    public String generateHash(String password) {

        return BCrypt.hashpw(password, BCrypt.gensalt());
    }

    public boolean checkPassword(String password, String hashedPassword) {
        return BCrypt.checkpw(password, hashedPassword);
    }
}
