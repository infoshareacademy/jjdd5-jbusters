package com.infoshareacademy.jbusters.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "users")
public class Users {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "USERS_ID")
    private int usersId;

    @Column(name = "USERS_EMAIL")
    @NotNull
    private String usersEmail;

    @Column(name = "USERS_PASSWORD")
    @NotNull
    private String usersPassword;

    @Column(name = "USERS_NAME")
    private String usersName;

    @Column(name = "USERS_SURNAME")
    private String usersSurname;

    @Column(name = "USERS_ROLE")
    private int usersRole;

    public Users() {
    }

    public Users(String usersEmail, String usersPassword, String usersName, String usersSurname, int usersRole) {
        this.usersEmail = usersEmail;
        this.usersPassword = usersPassword;
        this.usersName = usersName;
        this.usersSurname = usersSurname;
        this.usersRole = usersRole;
    }

    public int getUsersId() {
        return usersId;
    }

    public void setUsersId(int usersId) {
        this.usersId = usersId;
    }

    public String getUsersEmail() {
        return usersEmail;
    }

    public void setUsersEmail(String usersEmail) {
        this.usersEmail = usersEmail;
    }

    public String getUsersPassword() {
        return usersPassword;
    }

    public void setUsersPassword(String usersPassword) {
        this.usersPassword = usersPassword;
    }

    public String getUsersName() {
        return usersName;
    }

    public void setUsersName(String usersName) {
        this.usersName = usersName;
    }

    public String getUsersSurname() {
        return usersSurname;
    }

    public void setUsersSurname(String usersSurname) {
        this.usersSurname = usersSurname;
    }

    public int getUsersRole() {
        return usersRole;
    }

    public void setUsersRole(int usersRole) {
        this.usersRole = usersRole;
    }

}
