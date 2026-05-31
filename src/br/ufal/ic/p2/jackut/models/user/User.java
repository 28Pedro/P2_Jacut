package br.ufal.ic.p2.jackut.models.user;

import br.ufal.ic.p2.jackut.enums.FriendshipStates;

import java.util.*;

public class User {

    private String userName;
    private String password;
    private String id;
    private Queue<String> messengerNotifications;

    public User(){

        this.messengerNotifications = new ArrayDeque<>();
    }

    public User(String username, String password, String id) {
        this();
        userName = username;
        this.password = password;
        this.id = id;

    }

    public void addNotification(String chatMessengerId){
        messengerNotifications.add(chatMessengerId);
    }

    public Optional<String> popNotification(){
        return Optional.ofNullable(messengerNotifications.poll());
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

    public Queue<String> getMessengerNotifications() {
        return messengerNotifications;
    }

    public void setMessengerNotifications(Queue<String> messengerNotifications) {
        this.messengerNotifications = messengerNotifications;
    }

}
