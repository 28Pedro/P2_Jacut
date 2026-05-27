package br.ufal.ic.p2.jackut.models;

import br.ufal.ic.p2.jackut.exceptions.AtributoNaoPreenchido;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class User {

    private String name;
    private String userName;
    private String password;
    private String id;
    private Map<String,String> profileAttributes;

    public User(){
        this.profileAttributes = new HashMap<>();
    }

    public User(String username, String password, String name, String id) {
        this.name = name;
        userName = username;
        this.password = password;
        this.id = id;

        this.profileAttributes = new HashMap<>();
    }

    public String getUserAttribute(String attribute) throws AtributoNaoPreenchido{
        return switch (attribute){
            case "nome" -> getName();
            default -> {
                if(profileAttributes.containsKey(attribute)){
                    yield profileAttributes.get(attribute);
                }
                else{
                    throw new AtributoNaoPreenchido();
                }
            }
        };
    }

    public void addAttribute(String AttributeName, String AttributeValue){
        this.profileAttributes.put(AttributeName,AttributeValue);
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
}
