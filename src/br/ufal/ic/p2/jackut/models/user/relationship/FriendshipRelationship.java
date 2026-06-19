package br.ufal.ic.p2.jackut.models.user.relationship;

import br.ufal.ic.p2.jackut.enums.RelationshipType;

/**
 * Relacionamento de amizade entre usuários.
 */
public class FriendshipRelationship extends Relationship {

    public FriendshipRelationship() {
        super();
        setType(RelationshipType.FRIENDSHIP);
    }

    public FriendshipRelationship(String userId, String id) {
        super(userId, id, RelationshipType.FRIENDSHIP);
    }
}
