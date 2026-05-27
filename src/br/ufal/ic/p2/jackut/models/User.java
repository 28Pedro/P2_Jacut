package br.ufal.ic.p2.jackut.models;

import java.util.Optional;

public class User {

    private String name;
    private String userName;
    private String password;
    private String id;

    public User(){

    }

    public User(String username, String password, String name, String id) {
        this.name = name;
        userName = username;
        this.password = password;
        this.id = id;
    }

    public String getUserAttribute(String attribute){
        return switch (attribute){
            case "nome" -> getName();
            default -> null;
        };
    }

    public Optional<String> validateSection(String password){
         return this.password.matches(password) ? Optional.of(getId()) : Optional.empty();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
