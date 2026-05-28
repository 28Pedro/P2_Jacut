package br.ufal.ic.p2.jackut.models;

import br.ufal.ic.p2.jackut.enums.FriendshipStates;

import java.util.*;

public class User {

    private String name;
    private String userName;
    private String password;
    private String id;
    private Map<String,String> profileAttributes;
    private Map<FriendshipStates, List<String>> friendshipStates;

    public User(){
        this.profileAttributes = new HashMap<>();
        this.friendshipStates = new HashMap<>();

        friendshipStates.put(FriendshipStates.CURRENT,new ArrayList<>());
        friendshipStates.put(FriendshipStates.SENT,new ArrayList<>());
        friendshipStates.put(FriendshipStates.REQUESTED,new ArrayList<>());
    }

    public User(String username, String password, String name, String id) {
        this();
        this.name = name;
        userName = username;
        this.password = password;
        this.id = id;

    }

    public Optional<String> getUserAttribute(String attribute){
        if(attribute.equals("nome")) {
            return Optional.of(getName());

        }else {

            if(profileAttributes.containsKey(attribute)){
                return Optional.of(profileAttributes.get(attribute));

            } else{
                return Optional.empty();

                }
            }

        }

    public void addAttribute(String AttributeName, String AttributeValue){
        this.profileAttributes.put(AttributeName,AttributeValue);
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

    public Map<String, String> getProfileAttributes() {
        return profileAttributes;
    }

    public void setProfileAttributes(Map<String, String> profileAttributes) {
        this.profileAttributes = profileAttributes;
    }

    public Map<FriendshipStates, List<String>> getFriendshipStates() {
        return friendshipStates;
    }

    public void setFriendshipStates(Map<FriendshipStates, List<String>> friendshipStates) {
        this.friendshipStates = friendshipStates;
    }
}
