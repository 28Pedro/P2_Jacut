package br.ufal.ic.p2.jackut.models.user;

import java.util.*;

public class User {

    private String userName;
    private String password;
    private String id;

    public User(){}

    public User(String username, String password, String id) {
        this();
        userName = username;
        this.password = password;
        this.id = id;

    }

    public Optional<String> validateSection(String password){
         return this.password.matches(password) ? Optional.of(getId()) : Optional.empty();
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

}
