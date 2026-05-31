package br.ufal.ic.p2.jackut.models.user;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class Profile {

    private Map<String,String> profileAttributes;
    private String userId;
    private String Id;

    public Profile() {
        this.profileAttributes = new HashMap<>();
    }

    public Profile(String userId, String id) {
        this();
        this.userId = userId;
        Id = id;
    }

    public void addAttribute(String AttributeName, String AttributeValue){
        this.profileAttributes.put(AttributeName,AttributeValue);
    }

    public Optional<String> getUserAttribute(String attribute){

        if(profileAttributes.containsKey(attribute)){
            return Optional.of(profileAttributes.get(attribute));

        } else {
            return Optional.empty();
        }
    }

    public Map<String, String> getProfileAttributes() {
        return profileAttributes;
    }

    public void setProfileAttributes(Map<String, String> profileAttributes) {
        this.profileAttributes = profileAttributes;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }
}
