package br.ufal.ic.p2.jackut.models;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class ChatParticipantsKey {

    private Set<String> userIds;

    public ChatParticipantsKey() {
        this.userIds = new HashSet<>();
    }

    public ChatParticipantsKey(Set<String> userIds){
        this.userIds = userIds;
    }

    public Set<String> getUserIds() {
        return userIds;
    }

    public void setUserIds(Set<String> userIds) {
        this.userIds = userIds;
    }

    @Override
    public boolean equals(Object o){
        return Objects.equals(userIds,
                ((ChatParticipantsKey) o).userIds);
    }

    @Override
    public int hashCode(){
        return Objects.hash(userIds);
    }

}
