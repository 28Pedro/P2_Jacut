package br.ufal.ic.p2.jackut.models.chatmessenger;

import java.util.*;

public class ChatMessenger {

    private ChatParticipantsKey usersId;
    private String id;
    private Map<String, ChatUserState> messengerStates;

    public ChatMessenger() {
        this.messengerStates = new HashMap<>();
    }

    public ChatMessenger(String id, ChatParticipantsKey chatParticipantsKey){
        this();
        this.id = id;
        this.usersId = chatParticipantsKey;

        List<String> userList = this.usersId.getUserList();

        userList.stream().forEach(
                user -> messengerStates.put(user, new ChatUserState())
        );
    }

    public void sendMessenger(String messenger, String senderId) {

        messengerStates.forEach((userId, chatUserState) -> {
                    if (!userId.equals(senderId)) {
                        chatUserState.receiveMessenger(messenger);
                    }
                }
        );
    }

    public Optional<String> readMessage(String receiverId){
        ChatUserState chatUserState = messengerStates.get(receiverId);
        return chatUserState.readMessenger();
    }

    public ChatParticipantsKey getUsersId() {
        return usersId;
    }

    public void setUsersId(ChatParticipantsKey usersId) {
        this.usersId = usersId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Map<String, ChatUserState> getMessengerStates() {
        return messengerStates;
    }

    public void setMessengerStates(Map<String, ChatUserState> messengerStates) {
        this.messengerStates = messengerStates;
    }
}
