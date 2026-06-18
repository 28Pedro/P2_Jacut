package br.ufal.ic.p2.jackut.models.user;

import java.util.ArrayList;
import java.util.List;

/**
 * Representa a lista de comunidades das quais um usuário participa.
 */
public class CommunityList {

    private String id;
    private String userId;
    private List<String> communityNames;

    /**
     * Cria uma lista vazia para uso por mecanismos de serialização.
     */
    public CommunityList() {
        this.communityNames = new ArrayList<>();
    }

    /**
     * Cria uma lista de comunidades associada a um usuário.
     *
     * @param userId identificador do usuário dono da lista.
     * @param id identificador único da lista.
     */
    public CommunityList(String userId, String id) {
        this();
        this.userId = userId;
        this.id = id;
    }

    /**
     * Adiciona uma comunidade à lista.
     *
     * @param communityName nome da comunidade.
     */
    public void addCommunity(String communityName) {
        communityNames.add(communityName);
    }

    /**
     * Verifica se a lista contém uma comunidade.
     *
     * @param communityName nome da comunidade.
     * @return {@code true} se a comunidade estiver na lista.
     */
    public boolean containsCommunity(String communityName) {
        return communityNames.contains(communityName);
    }

    /**
     * Retorna a lista de comunidades no formato esperado pelos testes.
     *
     * @return comunidades no formato {@code {nome1,nome2}}.
     */
    public String buildCommunityList() {
        StringBuilder str = new StringBuilder();
        str.append('{');

        for (int i = 0; i < communityNames.size(); i++) {
            str.append(communityNames.get(i));

            if (i < communityNames.size() - 1) {
                str.append(',');
            }
        }

        str.append('}');
        return str.toString();
    }

    /**
     * Retorna o identificador único da lista.
     *
     * @return identificador da lista.
     */
    public String getId() {
        return id;
    }

    /**
     * Define o identificador único da lista.
     *
     * @param id identificador da lista.
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * Retorna o identificador do usuário dono da lista.
     *
     * @return identificador do usuário.
     */
    public String getUserId() {
        return userId;
    }

    /**
     * Define o identificador do usuário dono da lista.
     *
     * @param userId identificador do usuário.
     */
    public void setUserId(String userId) {
        this.userId = userId;
    }

    /**
     * Retorna os nomes das comunidades.
     *
     * @return lista de nomes de comunidades.
     */
    public List<String> getCommunityNames() {
        return communityNames;
    }

    /**
     * Define os nomes das comunidades.
     *
     * @param communityNames lista de nomes de comunidades.
     */
    public void setCommunityNames(List<String> communityNames) {
        this.communityNames = communityNames;
    }
}
