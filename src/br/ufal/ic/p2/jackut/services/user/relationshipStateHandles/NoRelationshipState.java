package br.ufal.ic.p2.jackut.services.user.relationshipStateHandles;

import br.ufal.ic.p2.jackut.enums.RelationshipState;
import br.ufal.ic.p2.jackut.models.user.relationship.Relationship;

/**
 * Estado usado quando ainda não existe vínculo entre os usuários.
 */
public class NoRelationshipState implements RelationshipStateHandle {

    @Override
    public void addRelationship(Relationship user, Relationship relatedUser) {
        user.addState(RelationshipState.SENT, relatedUser.getUserId());
        relatedUser.addState(RelationshipState.REQUESTED, user.getUserId());
    }
}
