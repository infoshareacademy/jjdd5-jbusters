package com.infoshareacademy.jbusters.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "USER_ID")
    private int userId;

    @Column(name = "USER_EMAIL")
    @NotNull
    private String userEmail;

    @Column(name = "USER_PASSWORD")
    @NotNull
    private String userPassword;

    @Column(name = "USER_NAME")
    private String userName;

    @Column(name = "USER_SURNAME")
    private String userSurname;

    @Column(name = "USER_ROLE")
    private int userRole;

    public User() {
    }

    public User(String userEmail, String userPassword, String userName, String userSurname, int userRole) {
        this.userEmail = userEmail;
        this.userPassword = userPassword;
        this.userName = userName;
        this.userSurname = userSurname;
        this.userRole = userRole;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserSurname() {
        return userSurname;
    }

    public void setUserSurname(String userSurname) {
        this.userSurname = userSurname;
    }

    public int getUserRole() {
        return userRole;
    }

    public void setUserRole(int userRole) {
        this.userRole = userRole;
    }
}
