package br.ufal.ic.p2.jackut.models.user.relationship;

import br.ufal.ic.p2.jackut.enums.RelationshipType;

/**
 * Relacionamento público entre fã e ídolo.
 */
public class FanRelationship extends Relationship {

    public FanRelationship() {
        super();
        setType(RelationshipType.FAN);
    }

    public FanRelationship(String userId, String id) {
        super(userId, id, RelationshipType.FAN);
    }
}
