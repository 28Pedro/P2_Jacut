package br.ufal.ic.p2.jackut.models.user;

import br.ufal.ic.p2.jackut.enums.FriendshipStates;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Friendship {

    private Map<FriendshipStates, List<String>> friendshipStates;
    private String userId;
    private String id;

    public Friendship() {

        this.friendshipStates = new HashMap<>();

        friendshipStates.put(FriendshipStates.CURRENT, new ArrayList<>());
        friendshipStates.put(FriendshipStates.SENT, new ArrayList<>());
        friendshipStates.put(FriendshipStates.REQUESTED, new ArrayList<>());
    }

    public Friendship(String userId,String id){
        this();
        this.userId = userId;
        this.id = id;
    }

    public boolean friendshipListContainsUser(String userName, FriendshipStates order){
        List<String> currentList = friendshipStates.get(order);

        return currentList.contains(userName);
    }

    public void addFriendshipState(String userName, FriendshipStates state) {
        List<String> currentList = friendshipStates.get(state);

        currentList.add(userName);

    }

    public void removeFridShipState(String userName, FriendshipStates state){
        List<String> currentList = friendshipStates.get(state);

        currentList.remove(userName);
    }

    public List<String> getFriendShipSateList(FriendshipStates state){

        return List.copyOf(friendshipStates.get(state));
    }

    public Map<FriendshipStates, List<String>> getFriendshipStates() {
        return friendshipStates;
    }

    public void setFriendshipStates(Map<FriendshipStates, List<String>> friendshipStates) {
        this.friendshipStates = friendshipStates;
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
