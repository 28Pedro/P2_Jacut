package br.ufal.ic.p2.jackut.models;

import br.ufal.ic.p2.jackut.enums.MessengerStates;

import java.util.*;

public class ChatMessenger {

    private ChatParticipantsKey users;
    private String id;
    private Map<String,Map<MessengerStates, Queue<String>>> messengerStates; // rapaz tem que ver isso dai

    public ChatMessenger() {

        this.usersId = new ArrayList<>();

        this.messengerStates = new HashMap<>();
        messengerStates.put(MessengerStates.RECEIVED,new ArrayDeque<>());
        messengerStates.put(MessengerStates.READ,new ArrayDeque<>());
    }

    public ChatMessenger(String id, String... usersId) {
        this();
        this.id = id;

        Collections.addAll(this.usersId,usersId);
    }

    public List<String> getUsersId() {
        return usersId;
    }

    public void setUsersId(List<String> usersId) {
        this.usersId = usersId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Map<MessengerStates, Queue<String>> getMessengerStates() {
        return messengerStates;
    }

    public void setMessengerStates(Map<MessengerStates, Queue<String>> messengerStates) {
        this.messengerStates = messengerStates;
    }
}
