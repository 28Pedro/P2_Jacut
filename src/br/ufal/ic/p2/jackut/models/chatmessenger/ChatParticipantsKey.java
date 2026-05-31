package br.ufal.ic.p2.jackut.models.chatmessenger;

import java.util.*;

public class ChatParticipantsKey {

    private Set<String> userIds;

    public ChatParticipantsKey() {
        this.userIds = new HashSet<>();
    }

    public ChatParticipantsKey(String ... userIds){
        this();
        Collections.addAll(this.userIds,userIds);
    }

    public List<String> getUserList(){
        return userIds.stream().toList();
    }

    public Set<String> getUserIds() {
        return userIds;
    }

    public void setUserIds(Set<String> userIds) {
        this.userIds = userIds;
    }

    @Override
    public boolean equals(Object o) {

        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ChatParticipantsKey that = (ChatParticipantsKey) o;
        return Objects.equals(userIds, that.userIds);
    }

    @Override
    public int hashCode(){
        return Objects.hash(userIds);
    }

}
