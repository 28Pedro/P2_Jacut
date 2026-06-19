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

    public Relationship() {
        initializeStates();
    }

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

    public boolean contains(String relatedUserId, RelationshipState state) {
        return relationshipStates.get(state).contains(relatedUserId);
    }

    public void addState(RelationshipState state, String relatedUserId) {
        if (!contains(relatedUserId, state)) {
            relationshipStates.get(state).add(relatedUserId);
        }
    }

    public void removeState(RelationshipState state, String relatedUserId) {
        relationshipStates.get(state).remove(relatedUserId);
    }

    public List<String> getStateList(RelationshipState state) {
        return relationshipStates.get(state);
    }

    public String getId() {
        return id;
    }

    public String getUserId() {
        return userId;
    }

    public RelationshipType getType() {
        return type;
    }

    public Map<RelationshipState, List<String>> getRelationshipStates() {
        return relationshipStates;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public void setType(RelationshipType type) {
        this.type = type;
    }

    public void setRelationshipStates(Map<RelationshipState, List<String>> relationshipStates) {
        this.relationshipStates = relationshipStates;
    }
}
