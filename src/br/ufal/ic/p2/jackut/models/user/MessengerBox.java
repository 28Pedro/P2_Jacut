package br.ufal.ic.p2.jackut.models.user;

import java.util.ArrayDeque;
import java.util.Optional;
import java.util.Queue;

public class MessengerBox {

    private Queue<String> messengerNotifications;
    private String userId;
    private String id;

    public MessengerBox(){
        this.messengerNotifications = new ArrayDeque<>();
    }

    public MessengerBox(String userId, String id){
        this();
        this.userId = userId;
        this.id = id;
    }

    public void addNotification(String chatMessengerId){
        messengerNotifications.add(chatMessengerId);
    }

    public Optional<String> popNotification(){
        return Optional.ofNullable(messengerNotifications.poll());
    }


    public Queue<String> getMessengerNotifications() {
        return messengerNotifications;
    }

    public void setMessengerNotifications(Queue<String> messengerNotifications) {
        this.messengerNotifications = messengerNotifications;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
