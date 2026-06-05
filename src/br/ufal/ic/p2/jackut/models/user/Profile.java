package br.ufal.ic.p2.jackut.models.user;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * Representa o perfil editável de um usuário.
 */
public class Profile {

    private Map<String,String> profileAttributes;
    private String userId;
    private String Id;

    /**
     * Cria um perfil vazio para uso por mecanismos de serialização.
     */
    public Profile() {
        this.profileAttributes = new HashMap<>();
    }

    /**
     * Cria um perfil associado a um usuário.
     *
     * @param userId identificador do usuário dono do perfil.
     * @param id identificador único do perfil.
     */
    public Profile(String userId, String id) {
        this();
        this.userId = userId;
        Id = id;
    }

    /**
     * Adiciona ou atualiza um atributo do perfil.
     *
     * @param AttributeName nome do atributo.
     * @param AttributeValue valor do atributo.
     */
    public void addAttribute(String AttributeName, String AttributeValue){
        this.profileAttributes.put(AttributeName,AttributeValue);
    }

    /**
     * Recupera um atributo do perfil.
     *
     * @param attribute nome do atributo.
     * @return valor do atributo, ou vazio se o atributo não existir.
     */
    public Optional<String> getUserAttribute(String attribute){

        if(profileAttributes.containsKey(attribute)){
            return Optional.of(profileAttributes.get(attribute));

        } else {
            return Optional.empty();
        }
    }

    /**
     * Retorna o mapa de atributos do perfil.
     *
     * @return mapa de atributos do perfil.
     */
    public Map<String, String> getProfileAttributes() {
        return profileAttributes;
    }

    /**
     * Define o mapa de atributos do perfil.
     *
     * @param profileAttributes mapa de atributos do perfil.
     */
    public void setProfileAttributes(Map<String, String> profileAttributes) {
        this.profileAttributes = profileAttributes;
    }

    /**
     * Retorna o identificador do usuário dono do perfil.
     *
     * @return identificador do usuário.
     */
    public String getUserId() {
        return userId;
    }

    /**
     * Define o identificador do usuário dono do perfil.
     *
     * @param userId identificador do usuário.
     */
    public void setUserId(String userId) {
        this.userId = userId;
    }

    /**
     * Retorna o identificador único do perfil.
     *
     * @return identificador do perfil.
     */
    public String getId() {
        return Id;
    }

    /**
     * Define o identificador único do perfil.
     *
     * @param id identificador do perfil.
     */
    public void setId(String id) {
        Id = id;
    }
}
