package br.ufal.ic.p2.jackut.models.community;

import java.util.ArrayList;
import java.util.List;

/**
 * Representa uma comunidade criada por um usuário do Jackut.
 */
public class Community {

    private String id;
    private String name;
    private String description;
    private String ownerUserId;
    private String ownerUserName;
    private String chatMessengerId;
    private List<String> membersUserIds;
    private List<String> membersUserNames;

    /**
     * Cria uma comunidade vazia para uso por mecanismos de serialização.
     */
    public Community() {
        this.membersUserIds = new ArrayList<>();
        this.membersUserNames = new ArrayList<>();
    }

    /**
     * Cria uma comunidade com dono, descrição e chat associado.
     *
     * @param id identificador único da comunidade.
     * @param name nome único da comunidade.
     * @param description descrição textual da comunidade.
     * @param ownerUserId identificador do usuário dono da comunidade.
     * @param ownerUserName login do usuário dono da comunidade.
     * @param chatMessengerId identificador do chat usado para mensagens da comunidade.
     */
    public Community(String id, String name, String description, String ownerUserId,
                     String ownerUserName, String chatMessengerId) {
        this();
        this.id = id;
        this.name = name;
        this.description = description;
        this.ownerUserId = ownerUserId;
        this.ownerUserName = ownerUserName;
        this.chatMessengerId = chatMessengerId;
        this.membersUserIds.add(ownerUserId);
        this.membersUserNames.add(ownerUserName);
    }

    /**
     * Adiciona um membro à comunidade.
     *
     * @param userId identificador do usuário adicionado.
     * @param userName login do usuário adicionado.
     */
    public void addMember(String userId, String userName) {
        membersUserIds.add(userId);
        membersUserNames.add(userName);
    }

    /**
     * Verifica se um usuário já participa da comunidade.
     *
     * @param userName login do usuário consultado.
     * @return {@code true} se o usuário já for membro.
     */
    public boolean containsMember(String userName) {
        return membersUserNames.contains(userName);
    }

    /**
     * Retorna os membros em formato textual esperado pelos testes.
     *
     * @return membros da comunidade no formato {@code {login1,login2}}.
     */
    public String buildMembersList() {
        StringBuilder str = new StringBuilder();
        str.append('{');

        for (int i = 0; i < membersUserNames.size(); i++) {
            str.append(membersUserNames.get(i));

            if (i < membersUserNames.size() - 1) {
                str.append(',');
            }
        }

        str.append('}');
        return str.toString();
    }

    /**
     * Retorna o identificador único da comunidade.
     *
     * @return identificador da comunidade.
     */
    public String getId() {
        return id;
    }

    /**
     * Define o identificador único da comunidade.
     *
     * @param id identificador da comunidade.
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * Retorna o nome da comunidade.
     *
     * @return nome da comunidade.
     */
    public String getName() {
        return name;
    }

    /**
     * Define o nome da comunidade.
     *
     * @param name nome da comunidade.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Retorna a descrição da comunidade.
     *
     * @return descrição da comunidade.
     */
    public String getDescription() {
        return description;
    }

    /**
     * Define a descrição da comunidade.
     *
     * @param description descrição da comunidade.
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Retorna o identificador do dono da comunidade.
     *
     * @return identificador do dono.
     */
    public String getOwnerUserId() {
        return ownerUserId;
    }

    /**
     * Define o identificador do dono da comunidade.
     *
     * @param ownerUserId identificador do dono.
     */
    public void setOwnerUserId(String ownerUserId) {
        this.ownerUserId = ownerUserId;
    }

    /**
     * Retorna o login do dono da comunidade.
     *
     * @return login do dono.
     */
    public String getOwnerUserName() {
        return ownerUserName;
    }

    /**
     * Define o login do dono da comunidade.
     *
     * @param ownerUserName login do dono.
     */
    public void setOwnerUserName(String ownerUserName) {
        this.ownerUserName = ownerUserName;
    }

    /**
     * Retorna o identificador do chat da comunidade.
     *
     * @return identificador do chat.
     */
    public String getChatMessengerId() {
        return chatMessengerId;
    }

    /**
     * Define o identificador do chat da comunidade.
     *
     * @param chatMessengerId identificador do chat.
     */
    public void setChatMessengerId(String chatMessengerId) {
        this.chatMessengerId = chatMessengerId;
    }

    /**
     * Retorna a lista de IDs dos membros.
     *
     * @return lista de IDs dos membros.
     */
    public List<String> getMembersUserIds() {
        return membersUserIds;
    }

    /**
     * Define a lista de IDs dos membros.
     *
     * @param membersUserIds lista de IDs dos membros.
     */
    public void setMembersUserIds(List<String> membersUserIds) {
        this.membersUserIds = membersUserIds;
    }

    /**
     * Retorna a lista de logins dos membros.
     *
     * @return lista de membros.
     */
    public List<String> getMembersUserNames() {
        return membersUserNames;
    }

    /**
     * Define a lista de logins dos membros.
     *
     * @param membersUserNames lista de membros.
     */
    public void setMembersUserNames(List<String> membersUserNames) {
        this.membersUserNames = membersUserNames;
    }
}
