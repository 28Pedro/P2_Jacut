package br.ufal.ic.p2.jackut.models.chatmessenger;

import java.util.ArrayDeque;
import java.util.Optional;
import java.util.Queue;

public class ChatUserState {

    private Queue<String> ReadMessengers;
    private Queue<String> UnreadMessengers;

    public ChatUserState() {
        ReadMessengers = new ArrayDeque<>();
        UnreadMessengers = new ArrayDeque<>();
    }

    public void receiveMessenger(String messenger){
        this.UnreadMessengers.add(messenger);
    }

    public Optional<String> readMessenger(){
        if(UnreadMessengers.isEmpty()){
            return Optional.empty();
        }

        Optional<String> messengerO = Optional.of(UnreadMessengers.poll());
        ReadMessengers.add(messengerO.get());

        return messengerO;
    }

    public Queue<String> getReadMessengers() {
        return ReadMessengers;
    }

    public void setReadMessengers(Queue<String> readMessengers) {
        ReadMessengers = readMessengers;
    }

    public Queue<String> getUnreadMessengers() {
        return UnreadMessengers;
    }

    public void setUnreadMessengers(Queue<String> unreadMessengers) {
        UnreadMessengers = unreadMessengers;
    }
}
