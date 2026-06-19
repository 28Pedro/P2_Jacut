package br.ufal.ic.p2.jackut.models.user.relationship;

import br.ufal.ic.p2.jackut.enums.RelationshipState;
import br.ufal.ic.p2.jackut.enums.RelationshipType;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

/**
 * Superclasse dos relacionamentos de um usuário.
 */
public abstract class Relationship {

    private String id;
    private String userId;
    private RelationshipType type;
    private Map<RelationshipState, List<String>> relationshipStates;

    /**
     * Cria um relacionamento vazio para uso pela desserializacao.
     */
    public Relationship() {
        initializeStates();
    }

    /**
     * Cria um relacionamento associado a um usuario e tipo.
     *
     * @param userId identificador do usuario dono.
     * @param id identificador unico do relacionamento.
     * @param type tipo do relacionamento.
     */
    public Relationship(String userId, String id, RelationshipType type) {
        this();
        this.id = id;
        this.userId = userId;
        this.type = type;
    }

    private void initializeStates() {
        this.relationshipStates = new EnumMap<>(RelationshipState.class);

        for (RelationshipState state : RelationshipState.values()) {
            relationshipStates.put(state, new ArrayList<>());
        }
    }

    /**
     * Verifica se um usuario relacionado esta em determinado estado.
     *
     * @param relatedUserId identificador do usuario relacionado.
     * @param state estado consultado.
     * @return {@code true} quando o usuario estiver no estado.
     */
    public boolean contains(String relatedUserId, RelationshipState state) {
        return relationshipStates.get(state).contains(relatedUserId);
    }

    /**
     * Inclui um usuario relacionado em um estado, sem duplicacao.
     *
     * @param state estado de relacionamento.
     * @param relatedUserId identificador do usuario relacionado.
     */
    public void addState(RelationshipState state, String relatedUserId) {
        if (!contains(relatedUserId, state)) {
            relationshipStates.get(state).add(relatedUserId);
        }
    }

    /**
     * Remove um usuario relacionado de um estado.
     *
     * @param state estado de relacionamento.
     * @param relatedUserId identificador do usuario relacionado.
     */
    public void removeState(RelationshipState state, String relatedUserId) {
        relationshipStates.get(state).remove(relatedUserId);
    }

    /**
     * Retorna os usuarios vinculados a um estado.
     *
     * @param state estado consultado.
     * @return lista de identificadores vinculados ao estado.
     */
    public List<String> getStateList(RelationshipState state) {
        return relationshipStates.get(state);
    }

    /**
     * Retorna o identificador do relacionamento.
     *
     * @return identificador unico.
     */
    public String getId() {
        return id;
    }

    /**
     * Retorna o identificador do usuario dono.
     *
     * @return identificador do usuario.
     */
    public String getUserId() {
        return userId;
    }

    /**
     * Retorna o tipo do relacionamento.
     *
     * @return tipo do relacionamento.
     */
    public RelationshipType getType() {
        return type;
    }

    /**
     * Retorna o mapa de usuarios por estado.
     *
     * @return estados e respectivos usuarios relacionados.
     */
    public Map<RelationshipState, List<String>> getRelationshipStates() {
        return relationshipStates;
    }

    /**
     * Define o identificador do relacionamento.
     *
     * @param id identificador unico.
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * Define o usuario dono do relacionamento.
     *
     * @param userId identificador do usuario.
     */
    public void setUserId(String userId) {
        this.userId = userId;
    }

    /**
     * Define o tipo do relacionamento.
     *
     * @param type tipo do relacionamento.
     */
    public void setType(RelationshipType type) {
        this.type = type;
    }

    /**
     * Define os usuarios relacionados em cada estado.
     *
     * @param relationshipStates mapa de estados e usuarios relacionados.
     */
    public void setRelationshipStates(Map<RelationshipState, List<String>> relationshipStates) {
        this.relationshipStates = relationshipStates;
    }
}
