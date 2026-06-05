package br.ufal.ic.p2.jackut.models.chatmessenger;

import java.util.*;

/**
 * Representa a chave lógica formada pelos participantes de um chat.
 */
public class ChatParticipantsKey {

    private Set<String> userIds;

    /**
     * Cria uma chave vazia de participantes.
     */
    public ChatParticipantsKey() {
        this.userIds = new HashSet<>();
    }

    /**
     * Cria uma chave a partir dos identificadores dos participantes.
     *
     * @param userIds identificadores dos usuários participantes.
     */
    public ChatParticipantsKey(String ... userIds){
        this();
        Collections.addAll(this.userIds,userIds);
    }

    /**
     * Retorna os participantes em formato de lista.
     *
     * @return lista de identificadores dos participantes.
     */
    public List<String> getUserList(){
        return userIds.stream().toList();
    }

    /**
     * Retorna o conjunto de participantes.
     *
     * @return conjunto de identificadores dos participantes.
     */
    public Set<String> getUserIds() {
        return userIds;
    }

    /**
     * Define o conjunto de participantes.
     *
     * @param userIds conjunto de identificadores dos participantes.
     */
    public void setUserIds(Set<String> userIds) {
        this.userIds = userIds;
    }

    /**
     * Compara chaves de participantes pela igualdade do conjunto de usuários.
     *
     * @param o objeto comparado.
     * @return {@code true} se as chaves representarem os mesmos participantes.
     */
    @Override
    public boolean equals(Object o) {

        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ChatParticipantsKey that = (ChatParticipantsKey) o;
        return Objects.equals(userIds, that.userIds);
    }

    /**
     * Calcula o código hash da chave.
     *
     * @return código hash baseado no conjunto de participantes.
     */
    @Override
    public int hashCode(){
        return Objects.hash(userIds);
    }

}
