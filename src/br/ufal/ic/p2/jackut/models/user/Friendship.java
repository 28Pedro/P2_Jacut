package br.ufal.ic.p2.jackut.models.user;

import br.ufal.ic.p2.jackut.enums.FriendshipStates;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Representa a estrutura de relacionamentos de amizade de um usuário.
 */
public class Friendship {

    private Map<FriendshipStates, List<String>> friendshipStates;
    private String userId;
    private String id;

    /**
     * Cria uma estrutura de amizade vazia para uso por mecanismos de serialização.
     */
    public Friendship() {

        this.friendshipStates = new HashMap<>();

        friendshipStates.put(FriendshipStates.CURRENT, new ArrayList<>());
        friendshipStates.put(FriendshipStates.SENT, new ArrayList<>());
        friendshipStates.put(FriendshipStates.REQUESTED, new ArrayList<>());
    }

    /**
     * Cria uma estrutura de amizade para um usuário.
     *
     * @param userId identificador do usuário dono da estrutura.
     * @param id identificador único da estrutura de amizade.
     */
    public Friendship(String userId,String id){
        this();
        this.userId = userId;
        this.id = id;
    }

    /**
     * Verifica se uma lista de estado contém determinado usuário ou amizade.
     *
     * @param userName identificador consultado na lista de estado.
     * @param order estado de amizade a ser consultado.
     * @return {@code true} se o identificador estiver na lista; {@code false} caso contrário.
     */
    public boolean friendshipListContainsUser(String userName, FriendshipStates order){
        List<String> currentList = friendshipStates.get(order);

        return currentList.contains(userName);
    }

    /**
     * Adiciona um identificador à lista de um estado de amizade.
     *
     * @param userName identificador a ser adicionado.
     * @param state estado em que o identificador será incluído.
     */
    public void addFriendshipState(String userName, FriendshipStates state) {
        List<String> currentList = friendshipStates.get(state);

        currentList.add(userName);

    }

    /**
     * Remove um identificador da lista de um estado de amizade.
     *
     * @param userName identificador a ser removido.
     * @param state estado de onde o identificador será removido.
     */
    public void removeFridShipState(String userName, FriendshipStates state){
        List<String> currentList = friendshipStates.get(state);

        currentList.remove(userName);
    }

    /**
     * Retorna uma cópia da lista de identificadores de um estado.
     *
     * @param state estado de amizade consultado.
     * @return cópia imutável da lista associada ao estado.
     */
    public List<String> getFriendShipSateList(FriendshipStates state){

        return List.copyOf(friendshipStates.get(state));
    }

    /**
     * Retorna o mapa de estados de amizade.
     *
     * @return mapa entre estados e listas de identificadores.
     */
    public Map<FriendshipStates, List<String>> getFriendshipStates() {
        return friendshipStates;
    }

    /**
     * Define o mapa de estados de amizade.
     *
     * @param friendshipStates mapa entre estados e listas de identificadores.
     */
    public void setFriendshipStates(Map<FriendshipStates, List<String>> friendshipStates) {
        this.friendshipStates = friendshipStates;
    }

    /**
     * Retorna o identificador do usuário dono da estrutura.
     *
     * @return identificador do usuário.
     */
    public String getUserId() {
        return userId;
    }

    /**
     * Define o identificador do usuário dono da estrutura.
     *
     * @param userId identificador do usuário.
     */
    public void setUserId(String userId) {
        this.userId = userId;
    }

    /**
     * Retorna o identificador único da estrutura de amizade.
     *
     * @return identificador da estrutura.
     */
    public String getId() {
        return id;
    }

    /**
     * Define o identificador único da estrutura de amizade.
     *
     * @param id identificador da estrutura.
     */
    public void setId(String id) {
        this.id = id;
    }
}
